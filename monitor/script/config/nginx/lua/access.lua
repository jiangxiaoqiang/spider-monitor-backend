local redis = require 'resty.redis'
-- Creates a redis object. 
-- In case of failures, returns nil and a string describing the error.
local cache = redis.new()
-- miliseconds unit
-- 600000 equals 10 minites
cache:set_timeout(600000)
local ok ,err = cache.connect(cache,'192.168.1.11','6379')
if not ok then
    ngx.say("failed to connect: ", err)
    return
end
local res, err = cache:auth("123456")
if not res then
    ngx.say("failed to authenticate: ", err)
    return
end
-- 判断 member 元素是否集合 key 的成员
-- member:ngx.var.remote_addr
-- key:white_list
-- 如果 member 元素是集合的成员，返回 1 
-- 如果 member 元素不是集合的成员，或 key 不存在，返回 0
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
fire_ban_count = 80
-- ..连接两个字符串
ip_ban_time, err = cache:get('ip_ban_time:' .. ngx.var.remote_addr)
if ip_ban_time == ngx.null then
    -- 初始ban次数
    ip_ban_time = 0
    res , err = cache:set('ip_ban_time:' .. ngx.var.remote_addr, ip_ban_time)
    -- the expire time unit is second
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
    -- set or reset each ip first count
    -- key contains ip and value contains time
    res , err = cache:set('time:' .. ngx.var.remote_addr , os.time())
    res , err = cache:set('count:' .. ngx.var.remote_addr , 1)
else
    ip_count = ip_count + 1
    res , err = cache:incr('count:' .. ngx.var.remote_addr)
    -- Set 是 String 类型的无序集合。集合成员是唯一的
    -- 添加，删除，查找的复杂度都是 O(1)
    -- 不支持对集合的某个元素设置过期时间
    res , err = cache:sadd('statistic_total_ip:' .. os.date('%x'), ngx.var.remote_addr)
    -- trigger ban action
    if ip_count >= fire_ban_count then
        res , err = cache:set('ban:' .. ngx.var.remote_addr , 1)
        res , err = cache:expire('ban:' .. ngx.var.remote_addr , ip_ban_time)
        res , err = cache:incrby('ip_ban_time:' .. ngx.var.remote_addr, ip_ban_time)
        if res > 10 then
            -- 如果连续被ban超过10次
            -- 加入黑名单
            res , err = cache:sadd('black_list', ngx.var.remote_addr)
        end
        res , err = cache:sadd('statistic_ban_ip:' .. os.date('%x'), ngx.var.remote_addr)
    end
end
::label::
local ok , err = cache:close()
