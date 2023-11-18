<script setup lang="ts">
import {ref, watch, inject} from 'vue';
import {ElMessage, ElMessageBox, ElTable} from 'element-plus';
import {http} from '@/utils/http';
import router from '@/router/index'
import CategoryEdit from "@/components/ProductManage/CategoryEdit.vue";

const props = defineProps(['parentId']);

interface ProdCategory {
  id: number,
  shopId: number,
  parentId: number,
  categoryName: string,
  status: number,
  level: number,
  createTime: string,
  children: null
}
const roleType = inject("roleType");

const tableRef = ref<InstanceType<typeof ElTable>>();

const page = ref({
  pageNum: 1,
  pageSize: 10,
  total: 100
});

const tableData = ref([]);

const parentId = ref(props.parentId);

const pageChange = () => {
  listData();
}

const pageSizeChange = () => {
  listData();
}

watch(props, (newParams) => {
  parentId.value = newParams.parentId;
  page.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  listData();
})

const listData = () => {
  const params = {
    url: "/product/category/page/list",
    data: {
      parentId: parentId.value,
      shopId: 0,
      page: {
        pageNum: page.value.pageNum,
        pageSize: page.value.pageSize,
        total: 0,
        pageSum: 0
      }
    },
    callBack: (res) => {
      const data = res.data;
      const records: ProdCategory[] = data.records;
      if (tableData.value.length !== 0) {
        tableData.value = tableData.value.slice(0, 0);
      }
      records.forEach((value) => {
        tableData.value.push(value);
      });
      page.value.total = data.page.total;
    }
  };
  http(params);
}

listData();

const drawParams = ref({
  categoryId: 0,
  parentId: 0
})

const openEdit = ref(false);

const addCategory = () => {
  drawParams.value.categoryId = 0;
  drawParams.value.parentId = parentId.value;
  openEdit.value = true;
}

const canEdit = (shopId) => {
  return (shopId === 0 && roleType.value === 2);
}

const switchChange = (category: ProdCategory) => {
  // console.log(category)
  updateCategory(category, 3);
}

const deleteCategory = (category: ProdCategory) => {
  ElMessageBox.confirm(
      "确认删除该类目?",
      "删除确定",
      {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "danger",
      }
  ).then(() => {
    updateCategory(category, 2);
  }).catch(() => {

  })
}

const switchCategory = (category: ProdCategory) => {
  router.push({
    name: 'prod-category-manage',
    params: {
      parentId: category.id
    }
  })
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

const editCategory = (category: ProdCategory) => {
  drawParams.value.categoryId = category.id;
  drawParams.value.parentId = category.parentId;
  openEdit.value = true;
}

const handleClose = () => {
  openEdit.value = false;
  listData();
}

const updateCategory = (category: ProdCategory, action: number) => {
  const params = {
    url: "/product/category/update",
    data: {
      action: action,
      data: {
        id: category.id,
        shopId: 0,
        parentId: category.parentId,
        categoryName: category.categoryName,
        status: category.status,
        level: category.level
      }
    },
    callBack: (res) => {
      ElMessage({
        message: "更新成功",
        type: "success"
      });
      listData();
    }
  }
  http(params);
}
</script>

<template>
  <el-card shadow="always">
    <template #header>
      <el-button
          type="primary"
          @click="addCategory">
        添加类别
      </el-button>
    </template>

    <el-table
        ref="tableRef"
        stripe
        border
        :data="tableData">
      <el-table-column property="id" label="ID" :width="80"/>
      <el-table-column property="categoryName" label="类别名称" :width="120"/>
      <el-table-column property="level" label="层级" :width="60"/>
      <el-table-column label="创建者" :width="100">
        <template #default="scope">
          <el-tag :type="getCreatorTag(scope.row.shopId).type">
            {{ getCreatorTag(scope.row.shopId).name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="设置" :width="270">
        <template #default="scope">
          <el-button
              size="small"
              type="default"
              @click="switchCategory(scope.row)"
              :disabled="scope.row.level !== 1">
            查看下级
          </el-button>
          <el-button
              size="small"
              type="warning"
              :disabled="scope.row.level === 1 || roleType === 3">
            绑定属性
          </el-button>
          <el-button
              size="small"
              type="success"
              :disabled="scope.row.level === 1 || roleType === 3">
            绑定参数
          </el-button>
        </template>
      </el-table-column>
      <el-table-column property="status" label="是否开启" :width="90">
        <template #default="scope">
          <el-switch
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              :disabled="canEdit(scope.row.shopId)"
              @change="switchChange(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column property="createTime" label="创建时间" :width="180"/>
      <el-table-column label="操作" :width="140" fixed="right">
        <template #default="scope">
          <el-button
              type="default"
              size="small"
              @click="editCategory(scope.row)"
              :disabled="canEdit(scope.row.shopId)">
            编辑
          </el-button>
          <el-button
              type="danger"
              size="small"
              @click="deleteCategory(scope.row)"
              :disabled="canEdit(scope.row.shopId)">
            删除
          </el-button>
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

  <CategoryEdit :category-id="drawParams.categoryId" :parent-id="drawParams.parentId" :open="openEdit"
                :handle-close="handleClose"/>
</template>

<style scoped>
.page {
  margin-top: 10px;
}
</style>