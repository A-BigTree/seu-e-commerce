<script setup lang="ts">

import {inject, ref} from "vue";
import {ElMessage, ElMessageBox, ElTable} from "element-plus";
import type {FormInstance} from "element-plus";
import {http} from '@/utils/http';
import {getDisplayPrice, getStandPrice, getProdStatusTag} from '@/utils';
import {IMAGE_URL} from '@/utils/config';
import router from "@/router";
import {Edit, Document} from "@element-plus/icons-vue";
import ProdSkuEdit from "@/components/ProductManage/ProdSkuEdit.vue";
import ProdReviewPage from "@/components/ProductManage/ProdReviewPage.vue";

const roleType = inject("roleType");

const tableRef = ref<InstanceType<typeof ElTable>>();

const page = ref({
  pageNum: 1,
  pageSize: 10,
  total: 100
});

const tableData = ref([]);

const formData = ref({
  prodId: 0,
  shopId: 0,
  prodName: "",
  status: -1,
  categoryId: [-1],
});

const dialogSkuOpen = ref(false);
const editProdId = ref(0);

const categories = ref([
  {
    value: 0,
    label: "",
    children: [
      {
        value: 1,
        label: ""
      }
    ]
  }
]);

const listData = () => {
  const params = {
    url: "/product/tob/prod/page/list",
    data: {
      prodId: formData.value.prodId,
      shopId: formData.value.shopId,
      prodName: formData.value.prodName,
      status: formData.value.status,
      categoryId: formData.value.categoryId[formData.value.categoryId.length - 1],
      page: page.value
    },
    callBack: (res) => {
      tableData.value = [];
      const data = res.data;
      data.records.forEach((value) => {
        tableData.value.push(value);
      });
      page.value.total = data.page.total;
    }
  }
  http(params);
}

const init = () => {
  const params = {
    url: "/product/category/all/get",
    method: "get",
    callBack: (res) => {
      const data = res.data;
      categories.value = [];
      categories.value.push({
        value: -1,
        label: "全部"
      });
      data.forEach(category => {
        const children = [];
        category.children.forEach(value => {
          children.push({
            value: value.id,
            label: value.categoryName
          });
        });
        if (children.length > 0) {
          categories.value.push({
            value: category.id,
            label: category.categoryName,
            children: children
          });
        }
      });
    }
  }
  http(params);
}

//初始化
listData();
init();

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

// 编辑
const editProd = (prodId) => {
  router.push({
    name: "product-edit",
    params: {
      prodId: prodId
    }
  })
}

// 编辑状态
const updateProdStatus = (prodId, status, message) => {
  const params = {
    url: "/product/tob/prod/status/update",
    data: {
      prodId: prodId,
      status: status,
      remark: message
    },
    callBack:(res) => {
      ElMessage({
        message: "操作成功",
        type: "success"
      })
    }
  };
  http(params);
  listData();
}
const editProdStatus = (prod, status) => {
  ElMessageBox.prompt("请输入操作原因", "确认消息", {
    confirmButtonText: "提交",
    cancelButtonText: "取消",
    inputPattern: /^.+$/,
    inputErrorMessage: "操作原因不能为空"
  }).then((value) => {
    if (value.action === 'confirm') {
      if (status !== -1) {
        updateProdStatus(prod.id, status, value.value);
      } else {
        const params1 = {
          url: "/product/tob/prod/update",
          data: {
            action: 2,
            data: prod
          },
          callBack: (res) => {
            ElMessage({
              message: "修改成功",
              type: "success"
            });
          }
        }
        http(params1);
      }

    }
  }).catch((value) => {
  });
  listData();
}

// 编辑SKU callBack
const editSkuCallBack = (status) => {
  const params = {
    url: "/product/tob/prod/status/update",
    data: {
      prodId: editProdId.value,
      status: status,
      remark: "修改SKU库存"
    }
  };
  http(params);
  dialogSkuOpen.value = false
  listData();
}

// 操作历史
const dialogOperateOpen = ref(false);

// 审核
const dialogReviewOpen = ref(false);

const dialogFormRef = ref<FormInstance>();

const dialogForm = ref({
  remark: "",
  reviewState: 1
});

const dialogFormRule = ref({
  reviewState: [
    {required: true, message: '审议结果不能为空', trigger: 'blur'}
  ],
  remark: [
    {required: true, message: '审核意见不能为空', trigger: 'blur'}
  ]
});

const submitReview = (ref) => {
  if (dialogForm.value.reviewState === 2) {
    dialogForm.value.remark = '审核通过';
  }
  if (!ref) return;
  ref.validate((valid) => {
    if (valid) {
      updateProdStatus(editProdId.value, dialogForm.value.reviewState, dialogForm.value.remark);
      dialogReviewOpen.value = false;
    }
  })
}
</script>

<template>
  <el-card shadow="always">
    <template #header>
      <el-form :inline="true" :model="formData">
        <el-form-item label="ID">
          <el-input type="number" v-model="formData.prodId"/>
        </el-form-item>
        <el-form-item label="商品名称">
          <el-input v-model="formData.prodName"/>
        </el-form-item>
        <el-form-item label="商品状态">
          <el-select v-model="formData.status">
            <el-option :value="-1" label="全部"/>
            <el-option :value="0" label="待审核"/>
            <el-option :value="1" label="审核失败"/>
            <el-option :value="2" label="待上架"/>
            <el-option :value="3" label="已上架"/>
            <el-option :value="4" label="已下架"/>
          </el-select>
        </el-form-item>

        <el-form-item v-if="roleType === 3" label="商家ID">
          <el-input type="number" v-model="formData.shopId"/>
        </el-form-item>

        <el-form-item label="商品分类">
            <el-cascader v-model="formData.categoryId" :options="categories"/>
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
      <el-table-column label="编号" prop="id" width="80"/>

      <el-table-column v-if="roleType === 3" label="商家ID" prop="shopId" width="80"/>

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
        <template #default="scope">
          <div style="text-align: center">
            <el-button type="primary" :icon="Edit" circle size="large"
                       @click="dialogSkuOpen = true;  editProdId = scope.row.id"/>
          </div>
        </template>

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
            <el-button type="default"
                       @click="dialogOperateOpen = true;  editProdId = scope.row.id"
                       size="small"
                       :icon="Document">操作日志</el-button>
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
              <el-button v-if="roleType === 2" type="warning" size="small" @click="editProd(scope.row.id)">编辑</el-button>
              <el-button v-else type="warning"
                         @click="dialogReviewOpen = true;dialogForm={remark: '', reviewState: 2};editProdId=scope.row.id"
                         size="small"
                         :disabled="scope.row.status !== 0">审核</el-button>
              <el-button v-if="roleType === 2"
                         @click="editProdStatus(scope.row, -1)"
                         type="danger"
                         size="small"
                         >删除</el-button>
              <el-button v-if="roleType === 3" type="danger" size="small" :disabled="scope.row.status === 0">下架</el-button>
            </el-row>
            <el-row class="top" v-if="roleType === 2">
              <el-button type="success" size="small"
                         :disabled="scope.row.status === 0"
                         @click="updateProdStatus(scope.row.id, 3, '商品上架')">上架</el-button>
              <el-button type="danger"
                         @click="editProdStatus(scope.row, 4)"
                         size="small"
                         :disabled="scope.row.status !== 3">下架</el-button>
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

  <el-dialog v-model="dialogSkuOpen" title="编辑SKU库存" width="70%">
    <ProdSkuEdit :prod-id="editProdId" :can-change="roleType === 2 ? 1 : 0" :call-back="editSkuCallBack"/>
  </el-dialog>

  <el-dialog v-model="dialogOperateOpen" title="操作历史">
    <ProdReviewPage :prod-id="editProdId"/>
  </el-dialog>

  <el-dialog v-model="dialogReviewOpen" title="商品审核" width="40%">
    <el-form :model="dialogForm"
             ref="dialogFormRef"
             :rules="dialogFormRule"
             status-icon>
      <el-form-item label="是否通过" prop="reviewState">
        <el-radio-group v-model="dialogForm.reviewState">
          <el-radio :label="2">审核通过</el-radio>
          <el-radio :label="1">审核不通过</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审核意见" prop="remark" v-if="dialogForm.reviewState !== 2">
        <el-input
            v-model="dialogForm.remark"
            placeholder="请输入审核意见"
            :autosize="{ minRows: 3, maxRows: 3 }"
            maxlength="200"
            show-word-limit
            type="textarea"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitReview(dialogFormRef)">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
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