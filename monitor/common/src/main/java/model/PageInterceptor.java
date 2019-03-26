package model;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class PageInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(PageInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) target;
            BoundSql boundSql = statementHandler.getBoundSql();
            Object obj = boundSql.getParameterObject();
            if (obj != null && obj instanceof Pageable) {
                Pageable pageable = (Pageable) obj;
                MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
                MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
                String selectId = mappedStatement.getId();
                String selectSql = boundSql.getSql();
                logger.debug("执行的方法名为: {}，原始的sql为: {}", selectId, selectSql);
                // 获取统计sql
                String countSql = getCountSql(selectSql);
                // 创建countBoundSql
                BoundSql countBoundSql = copyFromBoundSql(mappedStatement, boundSql, countSql);
                Connection connection = (Connection) invocation.getArgs()[0];
                PreparedStatement countStmt = connection.prepareStatement(countSql);
                DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, pageable, countBoundSql);
                parameterHandler.setParameters(countStmt);
                int total = getTotal(countStmt);
                //pageable.setTotalElements(total);
                // 组装分页sql
                StringBuilder sb = new StringBuilder();
                sb.append(selectSql);
                List<Sort> sorts = pageable.getSorts();
                if (pageable.getSorts() != null && sorts.size() > 0) {
                    sb.append(" order by ");
                    for (int i = 0; i < sorts.size(); i++) {
                        if (StringUtils.isNoneBlank(sorts.get(i).getOrderField())) {
                            String directionField = null;
                            if (String.valueOf(sorts.get(i).getDirection()).toUpperCase().equals("NULLSLAST")) {
                                directionField = " nulls last";
                            } else if (String.valueOf(sorts.get(i).getDirection()).toUpperCase().equals("RANDOM")) {
                                directionField = " ";
                            } else {
                                directionField = String.valueOf(sorts.get(i).getDirection());
                            }
                            sb.append(sorts.get(i).getOrderField())
                                    .append(" ")
                                    .append(directionField);

                            if (String.valueOf(sorts.get(0).getOrderField()).toUpperCase().equals("UPDATE_DATE")) {
                                sb.append(" NULLS LAST");
                            }

                            if(String.valueOf(sorts.get(0).getOrderField()).toUpperCase().equals("JDRQ")){
                                sb.append(" NULLS LAST");
                            }

                            if (i != sorts.size() - 1) {
                                sb.append(",");
                            }

                        }
                    }

                }

                //sb.append(" limit ")
                //        .append(pageable.getOffset())
                //        .append(", ")
                //        .append(pageable.getSize());
                String delegateBoundSql = sb.toString();
                metaStatementHandler.setValue("delegate.boundSql.sql", delegateBoundSql);
            }
        }
        return invocation.proceed();
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }


    @Override
    public void setProperties(Properties properties) {

    }


    private String getCountSql(String sql) {
        String loserSql = sql.toLowerCase();
        if (loserSql.indexOf("group") != -1) {
            return "select count(*) from ( " + sql + " )";
        }
        int index = loserSql.indexOf("from");
        String combineSql = ("select count(*) " + sql.substring(index, sql.length())).trim();
        if (combineSql.charAt(combineSql.length() - 1) == ';') {
            combineSql = combineSql.substring(0, combineSql.length() - 1);
        }
        return combineSql;
    }


    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }


    private int getTotal(PreparedStatement preparedStatement) {
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

}