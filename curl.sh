#!/bin/bash

echo ">>> 1. [Service A] 正常调用 (预期: 200 OK, 结果 30)"
curl -X GET "http://localhost:8080/api/add?a=10&b=20"
echo -e "\n"

echo ">>> 2. [Service A] 幂等性测试 (预期: 200 OK, 但数据库不新增记录)"
curl -X GET "http://localhost:8080/api/add?a=10&b=20"
echo -e "\n"

echo ">>> 3. [Service A] 异常参数测试 (a=0) (预期: 400 Param Error)"
curl -X GET "http://localhost:8080/api/add?a=0&b=20"
echo -e "\n"

echo ">>> 4. [Service A] 参数缺失测试 (预期: 400 Missing Param)"
curl -X GET "http://localhost:8080/api/add?a=10"
echo -e "\n"

echo ">>> 5. [Service B] 正常透传 (预期: 200 OK)"
curl -X GET "http://localhost:8081/api/call-add?a=50&b=50"
echo -e "\n"

echo ">>> 6. [Service B] 降级测试 (请先手动停止 Service A)"
echo "请手动执行: curl -X GET 'http://localhost:8081/api/call-add?a=50&b=50'"