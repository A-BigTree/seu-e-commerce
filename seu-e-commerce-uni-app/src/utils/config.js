// 测试
const domain = "http://localhost:8086"; //统一接口域名，测试环境
// 生产环境
// const domain = "https://www.abigtree.top/eshop/api"
// MD5加密
const salt = "eshop-app"


module.exports = {
    domain,
    salt,
}