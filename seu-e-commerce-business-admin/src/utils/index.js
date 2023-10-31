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

export {
    loadingConfig,
    countDown,
}
