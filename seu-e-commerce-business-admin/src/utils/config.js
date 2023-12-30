// const API_URL = "http://localhost:8086/eshop/api";

const API_URL = "https://www.abigtree.top/eshop/api"  // 正式环境

const TIMEOUT = 5000;

const IMAGE_URL = "https://www.abigtree.top/eshop/static/admin/image";

const DEFAULT_HEAD_IMAGE = "https://www.abigtree.top/eshop/static/admin/image/head/0.png"

const MD5_SALT = "eshop-admin";

const IMAGE_UPLOAD_URL = API_URL + "/file/manage/image/upload";

const WSS_URL = "wss://www.abigtree.top/eshop/socket";

// const WS_URL = "ws://localhost:8087/eshop/socket";

const WS_URL = "ws://124.70.146.224:8087/eshop/socket";


export {
    API_URL,
    TIMEOUT,
    IMAGE_URL,
    MD5_SALT,
    DEFAULT_HEAD_IMAGE,
    IMAGE_UPLOAD_URL,
    WS_URL,
    WSS_URL
}