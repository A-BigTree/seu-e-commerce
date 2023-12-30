// 测试
//const domain = "http://localhost:8086/eshop/api"; //统一接口域名，测试环境
// 生产环境
const domain = "https://www.abigtree.top/eshop/api"
// MD5加密
const salt = "eshop-app"

const picDomain = "https://www.abigtree.top/eshop/static/admin/image"

// WebSocket

const wssDomain = "wss://www.abigtree.top/eshop/socket"

// const wsDomain = "ws://localhost:8087/eshop/socket"

const wsDomain = "ws://124.70.146.224:8087/eshop/socket"


export  {
    domain,
    salt,
    picDomain,
    wsDomain,
    wssDomain
}
