<script setup lang="ts">

import {inject, ref} from "vue";
import {IMAGE_URL} from "@/utils/config";
import {Document, Edit} from "@element-plus/icons-vue";
import {getDisplayPrice, getOrderStatusTag} from "@/utils";
import {http} from "@/utils/http";
import {ElMessage, ElMessageBox} from "element-plus";

const roleType = inject("roleType");

const page = ref({
  pageNum: 1,
  pageSize: 10,
  total: 100
});

const tableData = ref([]);

const formData = ref({
  userId: 0,
  shopId: 0,
  orderNumber: "",
  status: -1,
  closeType: -1,
  payType: -1
});

const listData = () => {
  http({
    url: "/prod/order/page/list",
    data: {
      page: page.value,
      ...formData.value
    },
    callBack: (res) => {
      tableData.value = res.data.records;
      page.value.total = res.data.page.total;
    }
  })
}

listData();

const filterData = () => {
  listData();
}

const pageChange = (pageNum: number) => {
  page.value.pageNum = pageNum;
  listData();
}

const pageSizeChange = (pageSize: number) => {
  page.value.pageSize = pageSize;
  listData();
}

const dialogOperateOpen = ref(false);
const editOrderId = ref(0);

const deliveryProd =  (orderId) => {
  ElMessageBox.prompt("请输入运输单号", "确认消息", {
    confirmButtonText: "提交",
    cancelButtonText: "取消",
    inputPattern: /^.+$/,
    inputErrorMessage: "快递单号不能为空"
  }).then((value) => {
    if (value.action === 'confirm') {
      http({
        url: "/prod/order/status/change",
        data: {
          orderId: orderId,
          status: 3,
          param1: value.value
        },
        callBack: (res) => {
          ElMessage.success("发货成功");
          listData();
        }
      })
    }
  }).catch((value) => {
  });
}

</script>

<template>
  <el-card shadow="always">
    <template #header>
      <el-form :inline="true" :model="formData">
        <el-form-item label="订单编号">
          <el-input v-model="formData.orderNumber"/>
        </el-form-item>
        <el-form-item label="用户ID">
          <el-input type="number" v-model="formData.userId"/>
        </el-form-item>
        <el-form-item label="商品状态">
          <el-select v-model="formData.status">
            <el-option :value="-1" label="全部"/>
            <el-option :value="1" label="待支付"/>
            <el-option :value="2" label="待发货"/>
            <el-option :value="3" label="待收货"/>
            <el-option :value="4" label="已完成"/>
            <el-option :value="5" label="已取消"/>
          </el-select>
        </el-form-item>

        <el-form-item v-if="roleType === 3" label="商家ID">
          <el-input type="number" v-model="formData.shopId"/>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="filterData">筛选</el-button>
        </el-form-item>
      </el-form>
    </template>

    <el-table
        ref="tableRef"
        stripe
        border
        :data="tableData"
    >
      <el-table-column label="编号" prop="orderNumber" width="160"/>

      <el-table-column v-if="roleType === 3" label="商家ID" prop="shopId" width="80"/>

      <el-table-column label="图片" width="150">
        <template #default="scope">
          <div class="block" style="text-align: center">
            <el-avatar shape="square" :size="100" :src="IMAGE_URL + scope.row.pic"/>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="商品名称" prop="prodName" width="120"/>
      <el-table-column label="支付价格" width="100">
        <template #default="scope">
          {{ getDisplayPrice(scope.row.total) }}￥
        </template>
      </el-table-column>

      <el-table-column label="数量" prop="prodCount" width="60"/>

      <el-table-column label="订单状态" width="120">
        <template #default="scope">
          <div style="text-align: center">
            <el-tag :type="getOrderStatusTag(scope.row.status, scope.row.closeType).type" size="large">
              {{getOrderStatusTag(scope.row.status, scope.row.closeType).name}}
            </el-tag>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="创建/更新时间" width="160">
        <template #default="scope">
          <div style="text-align: center">
            <el-tag type="success" class="top">
              {{ scope.row.createTime }}
            </el-tag>
            <el-tag type="warning">
              {{ scope.row.updateTime }}
            </el-tag>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="140" fixed="right">
        <template #default="scope">
          <div style="text-align: center">
            <el-button @click="dialogOperateOpen = true;  editOrderId = scope.row.id">详情</el-button>
          </div>
          <div style="text-align: center;margin-top: 10px">
            <el-button type="danger" v-if="scope.row.status === 1" @click="">取消</el-button>
            <el-button type="primary" v-if="scope.row.status === 2" @click="deliveryProd(scope.row.id)">发货</el-button>
          </div>
        </template>
      </el-table-column>

    </el-table>

    <el-pagination class="page"
                   v-model:current-page="page.pageNum"
                   v-model:page-size="page.pageSize"
                   @current-change="pageChange"
                   @size-change="pageSizeChange"
                   :page-sizes="[5, 10, 20]"
                   :small="false"
                   :disabled="false"
                   :background="false"
                   layout="sizes, prev, pager, next, total, jumper"
                   :total="page.total"/>
  </el-card>
</template>

<style scoped>

</style>