<script setup lang="ts">
import {ref, watch} from 'vue';
import {ElTable} from 'element-plus';
import {http} from '@/utils/http';
import router from '@/router/index'

const props = defineProps(['parentId', 'roleType']);

interface ProdCategory {
  id: number,
  shopId: number,
  parentId: number,
  categoryName: string,
  status: number,
  level: number,
  createTime: string,
  propertyNum: number,
  parameterNum: number,
  children: null
}

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

const canEdit = (shopId) => {
  return (shopId === 0 && props.roleType === '2');
}

const switchChange = (category: ProdCategory) => {
  console.log(category)
}

const switchCategory = (category: ProdCategory) => {
  router.push({
    name: 'prod-category-manage',
    params: {
      parentId: category.id,
      roleType: props.roleType
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

</script>

<template>
  <el-card shadow="always">
    <template #header>
      <el-button
          type="primary"
          @click="">
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
      <el-table-column property="propertyNum" label="属性数量" :width="100"/>
      <el-table-column property="parameterNum" label="参数数量" :width="100"/>
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
              :disabled="scope.row.level === 1 || !canEdit(scope.row.shopId)">
            绑定属性
          </el-button>
          <el-button
              size="small"
              type="success"
              :disabled="scope.row.level === 1 || !canEdit(scope.row.shopId)">
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
      <el-table-column label="操作" :width="160" fixed="right">
        <template #default="scope">
          <el-button
              type="default"
              size="small"
              @click=""
              :disabled="canEdit(scope.row.shopId)">
            编辑
          </el-button>
          <el-button
              type="danger"
              size="small"
              @click=""
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
</template>

<style scoped>
.page {
  margin-top: 10px;
}
</style>