<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${package.Mapper}.${table.mapperName}">

    <select id="findList" resultType="${package.Entity}.${table.entityName}">
        select
            t1.*
        from
            ${table.name} t1
        <where>
            1 = 1
        </where>
    </select>

</mapper>