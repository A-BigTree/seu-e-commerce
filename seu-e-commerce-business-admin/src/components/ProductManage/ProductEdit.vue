<script setup lang="ts">

import {inject, ref, watch} from "vue";
import {http} from '@/utils/http';
import {getToken} from "@/utils/cookies";
import {getDisplayPrice} from '@/utils';
import type {FormInstance} from 'element-plus'
import {ElMessage, ElMessageBox, ElTable} from "element-plus";
import {IMAGE_URL, IMAGE_UPLOAD_URL} from '@/utils/config';
import {Plus} from "@element-plus/icons-vue";

const props = defineProps(['prodId']);

const propId = parseInt(props.prodId);

const roleType = inject("roleType");

const step = ref(0);

const formRef = ref<FormInstance>();

const skuRef = ref<FormInstance>();

const formData = ref({
  id: 0,
  categoryId: 0,
  status: 0,
  prodName: "",
  originPrice: 0,
  price: 0,
  brief: "",
  content: "",
  pic: [{
    name: "",
    url: "",
    response: {
      data: ""
    }
  }],
  images: [
    {
      name: "",
      url: "",
      response: {
        data: ""
      }
    }
  ],
  deliveryMode: 0,
  deliveryPrice: 0,
});

const skuForm = ref({
  skus: [
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
      pic: [{
        url: "",
        name: "",
        response: {
          data: ""
        }
      }],
      originPrice: 0,
      price: 0,
      stocks: 0
    }
  ],
  parameters: [
    {
      prop: "",
      value: ""
    }
  ]
});

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

const isLoadProp = ref(false);

const spProp = ref([
  {
    propName: "",
    checked: [],
    key: "",
    add: "",
    selfAdd: false,
    values: [{
      value: "",
      label: "",
    }]
  }
]);

const addProp = (item) => {
  if (item.add !== '') {
    if (item.values.find(value => {
      return value.value === item.add;
    })) return;
    item.values.push({
      label: item.add,
      value: item.add
    })
  }
}

const paProp = ref([
  {
    propName: "",
    checked: [],
    key: "",
    selfAdd: false,
    values: [{
      value: "",
      label: "",
    }]
  }
])

const init = () => {
  isLoadProp.value = false;
  formData.value = {
    id: 0,
    categoryId: 0,
    status: 0,
    prodName: "",
    originPrice: 0,
    price: 0,
    brief: "",
    content: "",
    pic: [],
    images: [],
    deliveryMode: 0,
    deliveryPrice: 0,
  };
  skuForm.value = {
    skus: [],
    parameters: []
  }
  const params = {
    url: "/product/category/all/get",
    method: "get",
    callBack: (res) => {
      const data = res.data;
      categories.value = [];
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
      console.log(categories.value);
    }
  }
  http(params);
  if (propId > 0) {
    const params2 = {
      url: "/product/tob/prod/info/get?id=" + propId,
      method: "get",
      callBack: (res) => {
        const data = res.data;
        const images = [];
        if (data.images) {
          data.images.forEach(img => {
            images.push({
              name: img,
              url: IMAGE_URL + img,
              response: {
                data: img
              }
            });
          });
        }
        formData.value = {
          id: data.id,
          categoryId: data.categoryId,
          status: 0,
          prodName: data.prodName,
          originPrice: getDisplayPrice(data.originPrice),
          price: getDisplayPrice(data.price),
          brief: data.brief,
          content: data.content,
          pic: [
            {
              name: data.pic,
              url: IMAGE_URL + data.pic,
              response: {
                data: data.pic
              }
            }
          ],
          images: images,
          deliveryMode: data.deliveryMode,
          deliveryPrice: data.deliveryPrice,
        };
        const skus = [];
        data.skus.forEach(sku => {
          const sk = JSON.parse(JSON.stringify(sku));
          sk.pic = {
            url: IMAGE_URL + sku.pic,
            name: sku.pic,
            response: {
              data: sku.pic
            }
          }
          sk.price = getDisplayPrice(sku.price);
          sk.originPrice = getDisplayPrice(sku.originPrice);
          skus.push(sk);
        });
        skuForm.value = {
          skus: skus,
          parameters: data.parameters
        }
      }
    }
    http(params2);
  }
}

watch(props, (newParams) => {
  init();
});

// 初始化
init();

const preStep = () => {
  step.value--;
}

const nextStep = (ref1) => {
  if (!ref1) return;
  ref1.validate((valid) => {
    if (valid) {
      if (step.value === 0) {
        if (formData.value.categoryId === 0) {
          ElMessage({
            message: "请选择商品属性",
            type: 'error'
          });
          return;
        }
      }
      step.value++;
      initSku();
    }
  })
}

const initSku = () => {
  if (step.value === 1 && !isLoadProp.value) {
    const params = {
      url: "/product/category/props/get?id=" + formData.value.categoryId[1] + "&type=" + -1,
      method: 'get',
      callBack: (res) => {
        spProp.value = [];
        paProp.value = [];
        res.data.forEach(prop => {
          const values = [];
          const propName = prop.prop.propName;
          prop.prop.value.forEach(value => {
            values.push({
              value: value.valueName,
              label: value.valueName
            });
          })
          if (prop.propType === 1) {
            spProp.value.push({
              propName: propName,
              checked: [],
              key: propName,
              add: "",
              selfAdd: prop.prop.selfAdd === 1,
              values: values
            });
          } else {
            paProp.value.push({
              propName: propName,
              checked: [],
              key: propName,
              selfAdd: prop.prop.selfAdd === 1,
              values: values
            });
          }
        });
        console.log(spProp.value)
      }
    }
    http(params);


  }
}

const refreshSku = () => {
  ElMessageBox.confirm(
      "原有SKU配置将会被删除",
      "刷新确定",
      {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "error",
      }
  ).then(() => {
    const res = spProp.value.reduce((prop, current) => {
      let res = [];
      prop.forEach(value => {
        current.checked.forEach(v2 => {
          res.push(value.concat([{
            prop: current.propName,
            value: v2
          }]))
        });
      });
      return res;
    }, [[]]);
    skuForm.value.skus = [];
    res.forEach(props => {
      let name = "";
      props.forEach(prop => {
        name = name + prop.value + " ";
      })
      skuForm.value.skus.push(
          {
            id: 0,
            prodId: formData.value.id,
            properties: props,
            skuName: name,
            skuCode: "",
            pic: [],
            originPrice: 0,
            price: 0,
            stocks: 0
          }
      )
    });
  });

}

const submitData = () => {

}


const formRules = ref({
  /*
  prodName: [
    {required: true, message: '商品价格不能为空', trigger: 'blur'}
  ],
  originPrice: [
    {required: true, message: '价格不能为空', trigger: 'blur'}
  ],
  price: [
    {required: true, message: '价格不能为空', trigger: 'blur'}
  ],
  brief: [
    {required: true, message: '商品买点不能为空', trigger: 'blur'}
  ],
  content: [
    {required: true, message: '详细描述不能为空', trigger: 'blur'}
  ],
  pic: [
    {required: true, message: '商品主图不能为空', trigger: 'blur'}
  ]*/
})

const skuRules = ref({})

const headers: Record<string, string> = {
  "Access-Control-Allow-Origin": "*",
  "Authorization": getToken()
}

const updateData: Record<string, string> = {
  "prefix": 'product'
}

const picSuccess = (response) => {
  console.log(response);
  console.log(formData.value.pic);
}

const checkPic = () => {
  console.log(formData.value.pic);
  console.log(formData.value.images);
}

</script>

<template>

  <el-card shadow="always" class="form" style="width: 1100px">
    <el-steps :active="step" finish-status="success">
      <el-step title="商品基本信息"/>
      <el-step title="商品规格设置"/>
      <el-step title="确认信息"/>
    </el-steps>
    <el-form
        v-if="step === 0"
        ref="formRef"
        :model="formData"
        :rules="formRules"
        class="form"
        status-icon
    >
      <el-form-item label="商品分类" prop="category">
        <el-cascader v-model="formData.categoryId" :options="categories"/>
      </el-form-item>
      <el-form-item label="商品名称" prop="prodName">
        <el-input v-model="formData.prodName" style="width: 300px;"/>
      </el-form-item>
      <el-form-item label="商品主图" prop="pic">
        <el-upload
            v-model:file-list="formData.pic"
            list-type="picture-card"
            name="image"
            :action="IMAGE_UPLOAD_URL"
            :data="updateData"
            :headers="headers"
            :limit="1"
            :on-success="picSuccess"
            :on-change="checkPic"
        >
          <el-icon>
            <Plus/>
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="商品原价" prop="originPrice">
        <el-input-number
            :precision="2"
            v-model="formData.originPrice"
            :controls="false" size="small"
            :min="0.01"/>
        &nbsp;&nbsp;￥
      </el-form-item>
      <el-form-item label="商品现价" prop="price">
        <el-input-number
            :precision="2"
            v-model="formData.price"
            :controls="false" size="small"
            :min="0.01"/>
        &nbsp;&nbsp;￥
      </el-form-item>
      <el-form-item label="商品买点" prop="brief">
        <el-input
            v-model="formData.brief"
            maxlength="50"
            type="textarea"
            show-word-limit
            style="width: 500px;"
        />
      </el-form-item>
      <el-form-item label="详细描述" prop="content">
        <el-input
            v-model="formData.content"
            maxlength="200"
            type="textarea"
            show-word-limit
            style="width: 500px"/>
      </el-form-item>
      <el-form-item label="商品封面" prop="images">
        <el-upload
            v-model:file-list="formData.images"
            list-type="picture-card"
            name="image"
            :action="IMAGE_UPLOAD_URL"
            :data="updateData"
            :headers="headers"
            :on-success="picSuccess"
            :on-change="checkPic"
        >
          <el-icon>
            <Plus/>
          </el-icon>
        </el-upload>
      </el-form-item>


      <el-form-item>

        <el-button v-if="step < 2" type="primary" @click="nextStep(formRef)">下一步</el-button>
      </el-form-item>

    </el-form>

    <el-form
        v-if="step === 1"
        ref="skuRef"
        :model="skuForm"
        :rules="skuRules"
        class="form"
        status-icon
    >
      <el-card>
        <template #header>
          选择规格
        </template>
        <div v-for="item in spProp">
          <el-form-item :label="item.propName" :key="item.key">
            <el-checkbox-group v-model="item.checked">
              <el-checkbox v-for="value in item.values" :label="value.value"/>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item :key="item.key">
            <el-input v-model="item.add" :placeholder="'新增 ' + item.propName" style="width: 300px">
              <template #append>
                <el-button @click="addProp(item)">添加</el-button>
              </template>
            </el-input>
          </el-form-item>
        </div>
        <el-form-item>
          <el-button type="primary" @click="refreshSku">刷新SKU配置</el-button>
        </el-form-item>
      </el-card>

      <el-card class="form">
        <template #header>
          配置SKU库存
        </template>
        <el-table
            border
            :data="skuForm.skus">
          <el-table-column label="规格图片">
            <template #default="scope">
              <el-upload v-model:file-list="skuForm.skus[scope.$index].pic"
                         :action="IMAGE_UPLOAD_URL"
                         :data="updateData"
                         :limit="1"
                         name="image"
                         :headers="headers">
                <el-icon>
                  <Plus/>
                </el-icon>
              </el-upload>
            </template>

          </el-table-column>

          <el-table-column v-for="(item, index) in spProp" :label="item.propName">
            <template #default="scope">
              {{ skuForm.skus[scope.$index].properties[index].value }}
            </template>
          </el-table-column>
          <el-table-column label="规格名称">
            <template #default="scope">
              <el-input v-model="skuForm.skus[scope.$index].skuName"/>
            </template>
          </el-table-column>
          <el-table-column label="原价">
            <template #default="scope">
              <el-input-number v-model="skuForm.skus[scope.$index].originPrice" :precision="2" :min="0.01"
                               :controls="false" style="width: 80px"/>
            </template>
          </el-table-column>
          <el-table-column label="售价">
            <template #default="scope">
              <el-input-number
                  v-model="skuForm.skus[scope.$index].price"
                  :precision="2"
                  :min="0.01"
                  :controls="false"
                  style="width: 80px"
              />
            </template>
          </el-table-column>
          <el-table-column label="库存">
            <template #default="scope">
              <el-input-number v-model="skuForm.skus[scope.$index].stocks" :min="1" :controls="false"
                               style="width: 80px"/>
            </template>
          </el-table-column>
          <el-table-column label="SKU编号" prop="skuCode"/>
        </el-table>
        <el-form-item class="form">
          <el-button type="primary">同步价格</el-button>
          <el-button type="primary">同步库存</el-button>
        </el-form-item>
      </el-card>

      <el-card class="form">
        <template #header>
          配置产品参数
        </template>
      </el-card>

      <el-form-item class="form">
        <el-button v-if="step > 0" type="primary" @click="preStep">上一步</el-button>
        <el-button v-if="step < 2" type="primary" @click="nextStep(skuRef)">下一步</el-button>
      </el-form-item>

    </el-form>


    <div class="form">
      <el-button v-if="step === 2" type="primary" @click="preStep">上一步</el-button>
      <el-button v-if="step === 2" type="primary" @click="submitData">提交</el-button>
    </div>
  </el-card>


</template>

<style scoped>
.form {
  margin-top: 20px;
}
</style>