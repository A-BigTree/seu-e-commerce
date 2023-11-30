<script setup lang="ts">

import {inject, ref} from "vue";
import {ElTable} from "element-plus";
import {http} from '@/utils/http';
import {getDisplayPrice, getStandPrice, getProdStatusTag} from '@/utils';
import {IMAGE_URL} from '@/utils/config';
import router from "@/router";
import {Edit, Document} from "@element-plus/icons-vue";

const roleType = inject("roleType");

const tableRef = ref<InstanceType<typeof ElTable>>();

const page = ref({
  pageNum: 1,
  pageSize: 10,
  total: 100
});

const tableData = ref([
  {
    id: 2,
    prodName: "测试商品添加",
    shopId: 9,
    status: 0,
    originPrice: 400000,
    price: 389900,
    categoryId: 3,
    totalStocks: 379,
    soldNum: 0,
    brief: "测试测试测试",
    content: "测试测试测试测试",
    pic: "/product/2023/11/29/1701269538487.png",
    deliveryMode: 0,
    deliveryPrice: 0,
    updateTime: "2023-11-29 23:37:55",
    createTime: "2023-11-29 22:56:36",
    parameters: [
      {
        prop: "手机-参数-系统",
        value: "安卓11"
      },
      {
        prop: "手机-参数-处理器",
        value: "天玑1000"
      }
    ]
  }
]);

const formData = ref({
  prodId: 0,
  shopId: 0,
  prodName: "",
  status: -1,
  categoryId: -1,
});


const listData = () => {
  const params = {
    url: "/product/tob/prod/page/list",
    data: {
      
    }
  }
}

const filterData = () => {
  listData();
}

// 分页
const pageChange = (pageNum: number) => {
  page.value.pageNum = pageNum;
  listData();
}
const pageSizeChange = (pageSize: number) => {
  page.value.pageSize = pageSize;
  listData();
}


</script>

<template>
  <el-card shadow="always">
    <template #header>
      <el-form :inline="true" :model="formData">

      </el-form>
    </template>

    <el-table
        ref="tableRef"
        stripe
        border
        :data="tableData"
    >
      <el-table-column label="编号" prop="id"/>
      <el-table-column label="图片">
        <template #default="scope">
          <div class="block" style="text-align: center">
            <el-avatar shape="square" :size="100" :src="IMAGE_URL + scope.row.pic"/>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="商品名称" prop="prodName"/>
      <el-table-column label="售价">
        <template #default="scope">
          {{ getDisplayPrice(scope.row.price) }}￥
        </template>
      </el-table-column>
      <el-table-column label="SKU库存" width="90">
        <div style="text-align: center">
          <el-button type="primary" :icon="Edit" circle size="large"/>
        </div>
      </el-table-column>
      <el-table-column label="销量" prop="soldNum" width="80"/>

      <el-table-column label="商品状态" width="100">
        <template #default="scope">
          <div style="text-align: center">
            <el-tag :type="getProdStatusTag(scope.row.status).type" size="large">
              {{getProdStatusTag(scope.row.status).name}}
            </el-tag>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="创建/更新时间" width="160">
        <template #default="scope">
          <div style="text-align: center">
            <el-button type="default" size="small" :icon="Document">操作日志</el-button>
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
          <div>
            <el-row>

              <el-button v-if="roleType === 2" type="warning" size="small">编辑</el-button>
              <el-button v-else type="warning" size="small" :disabled="scope.row.status !== 0">审核</el-button>
              <el-button v-if="roleType === 2" type="danger" size="small" :disabled="scope.row.status === 0">删除</el-button>
              <el-button v-if="roleType === 3" type="danger" size="small" :disabled="scope.row.status === 0">下架</el-button>
            </el-row>
            <el-row class="top" v-if="roleType === 2">
              <el-button type="success" size="small" :disabled="scope.row.status === 0">上架</el-button>
              <el-button type="danger" size="small" :disabled="scope.row.status === 0">下架</el-button>
            </el-row>
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
.page {
  margin-top: 10px;
}

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