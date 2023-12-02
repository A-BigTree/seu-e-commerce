import {ref} from "vue";

const loadingConfig = function (text) {
    return {
        lock: true,
        text: text,
        background: 'rgba(0, 0, 0, 0.7)'
    }
}

const countDown = (second, callBack) => {
    const counter = ref(0)
    let timer = -1
    counter.value = second;
    timer = window.setInterval(() => {
        counter.value--;
        callBack(counter.value);
        if (counter.value === 0) {
            clearInterval(timer);
        }
    }, 1000);
}

const getRoleName = (roleEnum) => {
    switch (roleEnum) {
        case 1:
            return '消费者';
        case 2:
            return '商家';
        default:
            return '平台管理';
    }
}

const getRegisterStateName = (registerState) => {
    switch (registerState) {
        case 0:
            return '待审核';
        case 1:
            return '注册成功';
        case 2:
            return '注册失败';
        default:
            return '已注销';
    }
}

const getRoleTag = (type) => {
    switch (type) {
        case 1:
            return 'success';
        case 2:
            return '';
        default:
            return 'warning';
    }
}

const getRegisterStateTag = (type) => {
    switch (type) {
        case 0:
            return 'warning'
        case 1:
            return 'success'
        default:
            return 'danger'
    }
}

const getCreatorTag = (shopId) => {
    if (shopId === 0) {
        return {
            name: '平台官方',
            type: "warning"
        };
    } else {
        return {
            name: '自定义',
            type: ""
        };
    }
}

const getDisplayPrice = (price) => {
    return price / 100;
}

const getStandPrice = (price) => {
    return price * 100;
}

const getProdStatusTag = (status) => {
    switch (status) {
        case 0:
            return {
                name: "待审核",
                type: "warning"
            };
        case 1:
            return {
                name: "审核失败",
                type: "danger"
            };
        case 2:
            return {
                name: "待上架",
                type: ""
            };
        case 3:
            return {
                name: "已上架",
                type: "success"
            };
        case 4:
            return {
                name: "已下架",
                type: "danger"
            };
        default:
            return {
                name:"",
                type:""
            }
    }
}

export {
    loadingConfig,
    countDown,
    getRoleName,
    getRegisterStateName,
    getRoleTag,
    getRegisterStateTag,
    getCreatorTag,
    getDisplayPrice,
    getStandPrice,
    getProdStatusTag,
}
