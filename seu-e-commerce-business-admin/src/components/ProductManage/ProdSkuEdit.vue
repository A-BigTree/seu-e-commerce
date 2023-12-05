<script setup>
import {ref, watch} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {IMAGE_URL} from "@/utils/config";
import {http} from "@/utils/http";
import {Edit} from "@element-plus/icons-vue";
import {getDisplayPrice, getStandPrice} from "@/utils";

const props = defineProps(['prodId', 'canChange', 'callBack']);
const prodId = ref(parseInt(props.prodId));
const canChange = ref(parseInt(props.canChange) === 1);

const prodData = ref({});

const skusData = ref([
  {
    id: 0,
    prodId: 0,
    properties: [
      {
        prop: "",
        value: ""
      }
    ],
    skuName: "",
    skuCode: "",
    pic: "",
    originPrice: 0,
    price: 0,
    stocks: 0
  }
])

const init = () => {
  const params = {
    url: "/product/tob/prod/info/get?id=" + prodId.value,
    method: "get",
    callBack: (res) => {
      const data = res.data;
      prodData.value = data;
      const skus = [];
      data.skus.forEach(sku => {
        const sk = JSON.parse(JSON.stringify(sku));
        sk.price = getDisplayPrice(sku.price);
        sk.originPrice = getDisplayPrice(sku.originPrice);
        skus.push(sk);
      });
      skusData.value = skus;
    }
  }
  http(params);
}

init();

// 监听模块更新
watch(props, (newParams) => {
  prodId.value = parseInt(newParams.prodId);
  init();
});

const syncPrice = () => {
  ElMessageBox.confirm(
      "将第一个SKU商品价格配置同步到其他规格",
      "刷新确定",
      {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "error",
      }
  ).then(() => {
    if (skusData.value.length > 1) {
      const originPrice = skusData.value[0].originPrice;
      const price = skusData.value[0].price;
      for (let i = 1; i < skusData.value.length; i++) {
        skusData.value[i].originPrice = originPrice;
        skusData.value[i].price = price;
      }
    }
  });

}

const syncStocks = () => {
  ElMessageBox.confirm(
      "将第一个SKU商品库存同步到其他规格",
      "刷新确定",
      {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "error",
      }
  ).then(() => {
    if (skusData.value.length > 1) {
      const stocks = skusData.value[0].stocks;
      for (let i = 1; i < skusData.value.length; i++) {
        skusData.value[i].stocks = stocks;
      }
    }
  })
}

const submitData = () => {
  prodData.value.totalStocks = skusData.value.reduce((pre, curr) => {
    return pre + curr.stocks;
  }, 0);
  const skus = [];
  skusData.value.forEach(sku => {
    const sk = JSON.parse(JSON.stringify(sku));
    sk.originPrice = getStandPrice(sku.originPrice);
    sk.price = getStandPrice(sku.price);
    skus.push(sk);
  });
  prodData.value.skus = skus;

  const params1 = {
    url: "/product/tob/prod/update",
    data: {
      action: 3,
      data: prodData.value
    },
    callBack: (res) => {
      ElMessage({
        message: "修改成功",
        type: "success"
      });
      props.callBack(prodData.value.status);
    }
  }
  http(params1);
}
</script>

<template>
  <el-table
      border
      :data="skusData">
    <el-table-column label="规格图片">
      <template #default="scope">
        <div class="block" style="text-align: center">
          <el-avatar v-if="scope.row.pic" shape="square" :size="50" :src="IMAGE_URL + scope.row.pic"/>
          <el-avatar v-else shape="square" :size="50" :icon="Edit"/>
        </div>
      </template>
    </el-table-column>

    <el-table-column label="SKU编号" prop="skuCode"/>

    <el-table-column label="规格名称" prop="skuName"/>

    <el-table-column label="原价">
      <template #default="scope">
        <el-input-number v-model="skusData[scope.$index].originPrice"
                         :precision="2" :min="0.01" :disabled="!canChange"
                         :controls="false" style="width: 120px"/>
      </template>
    </el-table-column>
    <el-table-column label="售价">
      <template #default="scope">
        <el-input-number
            :disabled="!canChange"
            v-model="skusData[scope.$index].price"
            :precision="2"
            :min="0.01"
            :controls="false"
            style="width: 120px"
        />
      </template>
    </el-table-column>
    <el-table-column label="库存">
      <template #default="scope">
        <el-input-number v-model="skusData[scope.$index].stocks" :min="1" :controls="false" :disabled="!canChange"
                         style="width: 120px"/>
      </template>
    </el-table-column>

  </el-table>
  <el-form-item class="top" v-if="canChange">
    <el-button type="primary" @click="syncPrice">同步价格</el-button>
    <el-button type="primary" @click="syncStocks">同步库存</el-button>
    <el-button type="warning" @click="submitData">提交</el-button>
  </el-form-item>
</template>

<style scoped>
.top {
  margin-top: 10px;
}

.block {
  flex: 1;
  text-align: center;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>