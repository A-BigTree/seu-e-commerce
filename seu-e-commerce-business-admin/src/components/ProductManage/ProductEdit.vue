<script setup lang="ts">

import {inject, ref, watch} from "vue";
import {http} from '@/utils/http';
import {getToken} from "@/utils/cookies";
import {getDisplayPrice, getStandPrice} from '@/utils';
import type {FormInstance} from 'element-plus'
import {ElMessage, ElMessageBox, ElTable} from "element-plus";
import {IMAGE_URL, IMAGE_UPLOAD_URL} from '@/utils/config';
import {Plus} from "@element-plus/icons-vue";
import router from "@/router";

const props = defineProps(['prodId']);

const propId = ref(parseInt(props.prodId));

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

const changeCategory = () => {
  isLoadProp.value = false;
}

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
    key: "",
    selfAdd: false,
    selected: "",
    values: [{
      value: "",
      label: "",
    }]
  }
])

const init = () => {
  isLoadProp.value = false;
  step.value = 0;
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
    }
  }
  http(params);
  if (propId.value > 0) {
    const params2 = {
      url: "/product/tob/prod/info/get?id=" + propId.value,
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
          deliveryPrice: getDisplayPrice(data.deliveryPrice),
        };
        const skus = [];
        data.skus.forEach(sku => {
          const sk = JSON.parse(JSON.stringify(sku));
          sk.pic = sku.pic ? [{
            url: IMAGE_URL + sku.pic,
            name: sku.pic,
            response: {
              data: sku.pic
            }
          }] : [];
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
  propId.value = parseInt(newParams.prodId);
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
      url: "/product/category/props/get?id=" +
          (formData.value.categoryId.constructor === Array ? formData.value.categoryId[1] : formData.value.categoryId)
          + "&type=" + -1,
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
            let selected = "";
            skuForm.value.parameters.forEach(p => {
              if (p.prop === propName) {
                selected = p.value;
              }
            })
            paProp.value.push({
              propName: propName,
              key: propName,
              selected: selected,
              selfAdd: prop.prop.selfAdd === 1,
              values: values
            });
          }
        });
      }
    }
    http(params);

    isLoadProp.value = true;
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
    if (skuForm.value.skus.length > 1) {
      const originPrice = skuForm.value.skus[0].originPrice;
      const price = skuForm.value.skus[0].price;
      for (let i = 1; i < skuForm.value.skus.length; i++) {
        skuForm.value.skus[i].originPrice = originPrice;
        skuForm.value.skus[i].price = price;
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
    if (skuForm.value.skus.length > 1) {
      const stocks = skuForm.value.skus[0].stocks;
      for (let i = 1; i < skuForm.value.skus.length; i++) {
        skuForm.value.skus[i].stocks = stocks;
      }
    }
  })
}

const submitData = () => {
  if (checkSkuData()) {
    // 基本信息
    const data = JSON.parse(JSON.stringify(formData.value));
    data.categoryId = formData.value.categoryId[1];
    data.originPrice = getStandPrice(formData.value.originPrice);
    data.price = getStandPrice(formData.value.price);
    data.pic = formData.value.pic[0].response.data;
    data.totalStocks = skuForm.value.skus.reduce((pre, curr) => {
      return pre + curr.stocks;
    }, 0);
    const images = [];
    formData.value.images.forEach(image => {
      images.push(image.response.data);
    });
    data.images = images;
    data.deliveryPrice = getStandPrice(formData.value.deliveryPrice);
    const params = [];
    paProp.value.forEach(prop => {
      params.push({
        prop: prop.propName,
        value: prop.selected
      })
    });
    data.parameters = params;
    // SKU信息
    const skus = [];
    skuForm.value.skus.forEach(sku => {
      const sk = JSON.parse(JSON.stringify(sku));
      sk.pic = sku.pic.length === 0 ? null : sku.pic[0].response.data;
      sk.originPrice = getStandPrice(sku.originPrice);
      sk.price = getStandPrice(sku.price);
      skus.push(sk);
    });
    data.skus = skus;
    const params1 = {
      url: "/product/tob/prod/update",
      data: {
        action: propId.value === 0 ? 1 : 3,
        data: data
      },
      callBack: (res) => {
        addReview(res.data);
        ElMessage({
          message: propId.value === 0 ? "添加成功" : "修改成功",
          type: "success"
        });
        step.value += 2;
      }
    }
    http(params1);
  }
}

const checkSkuData = () => {
  let isOk = false;
  let message = "";
  if (skuForm.value.skus.length === 0) {
    message = "SKU未配置";
  } else if (paProp.value.find(value => {
    return value.selected === '';
  })) {
    message = "商品参数未配置";
  } else {
    isOk = true;
  }
  if (!isOk) {
    ElMessage({
      message: message,
      type: "error"
    });
  }
  return isOk;
}

const addReview = (prodID) => {
  const params = {
    url: "/product/tob/prod/status/update",
    data: {
      prodId: prodID,
      status: 0,
      remark: propId.value === 0 ? "添加商品" : "修改商品"
    }
  };
  http(params);
}

const formRules = ref({
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
  ]
})

const headers: Record<string, string> = {
  "Access-Control-Allow-Origin": "*",
  "Authorization": getToken()
}

const updateData: Record<string, string> = {
  "prefix": 'product'
}

const backHome = () => {
  router.push("/product/manage");
}
</script>

<template>

  <el-card shadow="always" class="form" style="width: 1100px">
    <el-steps :active="step" finish-status="success">
      <el-step title="商品基本信息"/>
      <el-step title="商品规格设置"/>
      <el-step :title="propId === 0 ? '添加成功' : '修改成功'"/>
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
        <el-cascader v-model="formData.categoryId" :options="categories" @change="changeCategory"/>
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
        >
          <el-icon>
            <Plus/>
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="配送模式">
        <el-select v-model="formData.deliveryMode">
          <el-option label="免运费" :value="0"/>
          <el-option label="不包邮" :value="1"/>
          <el-option label="京东速递" :value="2"/>
        </el-select>
      </el-form-item>
      <el-form-item label="配送费用">
        <el-input-number v-model="formData.deliveryPrice"
                         :controls="false" :precision="2" :min="0" :disabled="formData.deliveryMode === 0"
                         size="small"/>
        &nbsp;&nbsp;￥
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
                         list-type="picture"
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
          <el-button type="primary" @click="syncPrice">同步价格</el-button>
          <el-button type="primary" @click="syncStocks">同步库存</el-button>
        </el-form-item>
      </el-card>

      <el-card class="form">
        <template #header>
          配置产品参数
        </template>
        <el-form-item v-for="item in paProp"
                      :label="item.propName"
        >
          <el-select
              v-model="item.selected"
              filterable
              :allow-create="item.selfAdd"
              placeholder="选择参数">
            <el-option v-for="value in item.values" :value="value.value" :label="value.label"/>
          </el-select>
        </el-form-item>
      </el-card>

      <el-form-item class="form">
        <el-button type="info" @click="preStep">上一步</el-button>
        <el-button type="danger" @click="submitData(skuRef)">提交</el-button>
      </el-form-item>

    </el-form>

    <el-result
        v-if="step===3"
        icon="success"
        title="更新成功"
        sub-title="返回商品管理界面"
    >
      <template #extra>
        <el-button type="primary" @click="backHome">返回</el-button>
      </template>
    </el-result>
  </el-card>


</template>

<style scoped>
.form {
  margin-top: 20px;
}
</style>