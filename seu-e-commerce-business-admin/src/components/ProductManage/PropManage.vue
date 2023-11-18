<script setup lang="ts">
import {inject, ref} from "vue";
import {ElMessage, ElMessageBox, ElTable} from "element-plus";
import {http} from '@/utils/http';
import {getCreatorTag} from '@/utils';
import PropEdit from "@/components/ProductManage/PropEdit.vue";

const roleType = inject("roleType");

interface ProductProp {
  id: number,
  propName: string,
  propType: number,
  shopId: number,
  selfAdd: number,
  createTime: string,
  value: null
}

const tableRef = ref<InstanceType<typeof ElTable>>();

const page = ref({
  pageNum: 1,
  pageSize: 10,
  total: 100
});

const tableData = ref([]);

const listData = () => {
  const params = {
    url: "/product/category/prop/list",
    data: {
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
      const records = data.records;
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

const pageChange = () => {
  listData();
}

const pageSizeChange = () => {
  listData();
}

const canEdit = (shopId) => {
  return (shopId === 0 && roleType.value === 2);
}

const getPropTypeTag = (propType) => {
  if (propType === 1) {
    return {
      name: "商品规格",
      type: "warning"
    };
  } else {
    return {
      name: "商品参数",
      type: "success"
    }
  }
}

const getSelfAddTag = (selfAdd) => {
  if (selfAdd === 1) {
    return {
      name: "可扩展",
      type: "success"
    };
  } else {
    return {
      name: "不可扩展",
      type: "danger"
    }
  }
}

const drawParams = ref({
  propId: 0,
  open: false
})

const handleClose = () => {
  drawParams.value.open = false;
  listData();
}

const addProp = () => {
  drawParams.value.propId = 0;
  drawParams.value.open = true;
}

const editProp = (id) => {
  drawParams.value.propId = id;
  drawParams.value.open = true;
}

const deleteProp = (id) => {
  ElMessageBox.confirm(
      "确认删除该类目?",
      "删除确定",
      {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "danger",
      }
  ).then(() => {
    const param = {
      url: "/product/category/prop/update",
      data: {
        action: 2,
        data: {
          id: id,
          createTime: '',
          value: []
        }
      },
      callBack: (res) => {
        ElMessage({
          message: "删除成功",
          type: "success"
        });
        listData();
      }
    };
    http(param);
  }).catch(() => {

  })
}

</script>

<template>
  <el-card shadow="always">
    <template #header>
      <el-button
          type="primary"
          @click="addProp">
        添加属性
      </el-button>
    </template>

    <el-table
        ref="tableRef"
        stripe
        border
        :data="tableData">
      <el-table-column property="id" label="ID" :width="100"/>
      <el-table-column property="propName" label="属性名称" :width="250"/>
      <el-table-column property="propType" label="属性类型" :width="100">
        <template #default="scope">
          <el-tag :type="getPropTypeTag(scope.row.propType).type">
            {{ getPropTypeTag(scope.row.propType).name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column property="selfAdd" label="是否可扩展" :width="130">
        <template #default="scope">
          <el-tag :type="getSelfAddTag(scope.row.selfAdd).type">
            {{ getSelfAddTag(scope.row.selfAdd).name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建者" :width="100">
        <template #default="scope">
          <el-tag :type="getCreatorTag(scope.row.shopId).type">
            {{ getCreatorTag(scope.row.shopId).name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column property="createTime" label="创建时间" :width="180"/>
      <el-table-column label="操作" :width="140" fixed="right">
        <template #default="scope">
          <el-button
              type="default"
              size="small"
              @click="editProp(scope.row.id)">
            编辑
          </el-button>
          <el-button
              type="danger"
              size="small"
              @click="deleteProp(scope.row.id)"
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

  <PropEdit :prop-id="drawParams.propId" :open="drawParams.open" :handle-close="handleClose"/>
</template>

<style scoped>
.page {
  margin-top: 10px;
}
</style>