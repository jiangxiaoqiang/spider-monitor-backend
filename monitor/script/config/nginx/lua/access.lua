local redis = require 'resty.redis'
local cache = redis.new()
local ok ,err = cache.connect(cache,'10.20.1.11','6379')
if not ok then
    ngx.say("failed to connect: ", err)
    return
end
local res, err = cache:auth("eEUuNIMEIBsCF")
if not res then
    ngx.say("failed to authenticate: ", err)
    return
end
cache:set_timeout(600000)
is_white ,err = cache:sismember('white_list', ngx.var.remote_addr)
if is_white == 1 then
    goto label
end
is_black ,err = cache:sismember('black_list', ngx.var.remote_addr)
if is_black == 1 then
    ngx.exit(ngx.HTTP_FORBIDDEN)
    goto label
end
ip_time_out = 180
connect_count = 80
ip_ban_time, err = cache:get('ip_ban_time:' .. ngx.var.remote_addr)
if ip_ban_time == ngx.null then
    ip_ban_time = 300
    res , err = cache:set('ip_ban_time:' .. ngx.var.remote_addr, ip_ban_time)
    res , err = cache:expire('ip_ban_time:' .. ngx.var.remote_addr, 43200)
end
is_ban , err = cache:get('ban:' .. ngx.var.remote_addr)
if tonumber(is_ban) == 1 then
    local source = ngx.encode_base64(ngx.var.scheme .. '://' ..
            ngx.var.host .. ':' .. ngx.var.server_port .. ngx.var.request_uri)
    local dest = 'spider detected!!!'
    ngx.redirect(dest,302)
    goto label
end
start_time , err = cache:get('time:' .. ngx.var.remote_addr)
ip_count , err = cache:get('count:' .. ngx.var.remote_addr)
if start_time == ngx.null or os.time() - tonumber(start_time) > ip_time_out then
    res , err = cache:set('time:' .. ngx.var.remote_addr , os.time())
    res , err = cache:set('count:' .. ngx.var.remote_addr , 1)
else
    ip_count = ip_count + 1
    res , err = cache:incr('count:' .. ngx.var.remote_addr)
    res , err = cache:sadd('statistic_total_ip:' .. os.date('%x'), ngx.var.remote_addr)
    if ip_count >= connect_count then
        res , err = cache:set('ban:' .. ngx.var.remote_addr , 1)
        res , err = cache:expire('ban:' .. ngx.var.remote_addr , ip_ban_time)
        res , err = cache:incrby('ip_ban_time:' .. ngx.var.remote_addr, ip_ban_time)
        res , err = cache:sadd('statistic_ban_ip:' .. os.date('%x'), ngx.var.remote_addr)
    end
end
::label::
local ok , err = cache:close()
