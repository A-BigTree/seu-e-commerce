<script setup lang="ts">
import {inject, ref, watch} from "vue";
import type {FormInstance} from "element-plus";
import {http} from '@/utils/http';
import {ElMessage} from "element-plus";
import {Delete} from "@element-plus/icons-vue";
import {getCreatorTag} from '@/utils';

const props = defineProps(['categoryId', 'propType', 'open', 'handleClose'])

const roleType = inject("roleType");

const drawOpen = ref(false);

const formRef = ref<FormInstance>();

const formData = ref({
  categoryId: 0,
  propType: 0,
  props: [
    {
      key: 0,
      id: 0,
      categoryId: 0,
      propId: 0,
      propType: 0,
      propName: '',
      selfAdd: 0,
      values: [
        {
          valueName: '',
          shopId: 0
        }
      ]
    }
  ]
});

const selectedProp = ref('');

const propsData = ref([
  {
    id: 0,
    propName: '',
    shopId: 0
  }
])

const listProps = () => {
  const param = {
    url: "/product/category/props/get?id=" + props.categoryId + "&type=" + props.propType,
    method: 'get',
    callBack: (res) => {
      const data = res.data;
      const propsValue = [];
      data.forEach(value => {
        const values = [];
        value.prop.value.forEach(v => {
          values.push({
            valueName: v.valueName,
            shopId: v.shopId
          })
        });
        propsValue.push({
          key: value.id,
          id: value.id,
          categoryId: value.categoryId,
          propId: value.propId,
          propType: value.propType,
          propName: value.prop.propName,
          selfAdd: value.prop.selfAdd,
          values: values
        })
      });
      formData.value.categoryId = props.categoryId;
      formData.value.propType = props.propType;
      formData.value.props = propsValue;
    }
  }
  http(param);
}

const getAllProps = () => {
  const params = {
    url: "/product/category/prop/list",
    data: {
      shopId: 0,
      propType: props.propType,
      page: {
        pageNum: 1,
        pageSize: 100,
        total: 0,
        pageSum: 0
      }
    },
    callBack: (res) => {
      const data = res.data;
      propsData.value = [];
      data.records.forEach(value => {
        propsData.value.push({
          id: value.id,
          propName: value.propName,
          shopId: value.shopId
        })
      })
    }
  };
  http(params);
}

const addProp = () => {
  if (selectedProp.value === '') {
    ElMessage({
      message: "请选择绑定属性",
      type: "warning"
    });
    return;
  }
  for (let i = 0; i < formData.value.props.length; i++) {
    if (selectedProp.value === formData.value.props[i].propId) {
      ElMessage({
        message: "属性已绑定请检查",
        type: "warning"
      });
      return;
    }
  }
  const param = {
    url: "/product/category/prop/get?id=" + selectedProp.value,
    method: 'get',
    callBack: (res) => {
      const data = res.data;
      const values = [];
      data.value.forEach(value => {
        values.push({
          valueName: value.valueName,
          shopId: value.shopId
        })
      });
      formData.value.props.push({
        key: Date.now(),
        id: 0,
        categoryId: formData.value.categoryId,
        propId: data.id,
        propType: data.propType,
        propName: data.propName,
        selfAdd: data.selfAdd,
        values: values
      })
    }
  };
  http(param);
}

const init = () => {
  getAllProps();
  listProps();
  selectedProp.value = '';
}

watch(props, (newParams) => {
  drawOpen.value = newParams.open;
  if (drawOpen.value) {
    init();
  }
})

const submitEdit = (form) => {
  if (!form) return;
  form.validate((valid) => {
    if (valid) {
      const prop = [];
      formData.value.props.forEach(value => {
        prop.push({
          id: value.id,
          categoryId: value.categoryId,
          propId: value.propId,
          shopId: 0,
          propType: formData.value.propType
        });
      })
      const param = {
        url: "/product/category/props/update",
        data: {
          categoryId: formData.value.categoryId,
          propType: formData.value.propType,
          shopId: 0,
          props: prop
        },
        callBack: (res) => {
          ElMessage({
            message: "绑定成功",
            type: "success"
          });
          drawOpen.value = false;
        }
      };
      http(param);
    }
  });
}

const deleteValue = (value) => {
  const index = formData.value.props.indexOf(value);
  if (index !== -1) {
    formData.value.props.splice(index, 1);
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

</script>

<template>
  <el-drawer
      v-model="drawOpen"
      direction="rtl"
      title="绑定属性"
      @close="props.handleClose">
    <el-form>
      <el-form-item>
        <el-select v-model="selectedProp" placeholder="选择要绑定的属性" :filterable="true">
          <el-option
              v-for="item in propsData"
              :key="item.id"
              :label="item.propName"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="addProp"
                   type="primary">
          添加属性
        </el-button>
      </el-form-item>
    </el-form>


    <el-divider/>
    <el-form
        ref="formRef"
        :model="formData"
    >
      <el-form-item
          v-for="value in formData.props"
          :key="value.key"
      >
        <el-card style="width: 300px;" shadow="always">
          <template #header>
            <div class="card-header">
              <span>
                {{ value.propName }}
                <el-tag :type="getSelfAddTag(value.selfAdd).type">
                  {{ getSelfAddTag(value.selfAdd).name }}
                </el-tag>
              </span>
              <el-button type="danger" @click="deleteValue(value)" :icon="Delete"/>
            </div>
          </template>
          <span v-for="item in value.values">
            <el-tag :type="getCreatorTag(item.shopId).type">
              {{ item.valueName }}
            </el-tag>
          </span>

        </el-card>
      </el-form-item>
      <el-form-item>
        <el-button @click="submitEdit(formRef)" type="primary" size="large">
          提交
        </el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<style scoped>

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>