<template>
  <VSheet>
    <TabActionRow  title="Изменить продукт">
      <VSheet v-if="isProductEditing" class="bg-transparent">
        <ProductEdit
          :id="editingProductId"
          @on-close="isProductEditing = false"
          @on-confirm="confirmEditing"
        />
      </VSheet>
      <VSheet
        v-else
        width="100%"
        class=bg-red
      >
        <ProductList
          type="row"
          @on-card-click="editCardClick"
        />
      </VSheet>
    </TabActionRow>
  </VSheet>
</template>

<script setup>
import TabActionRow from '@/components/user/tabs/TabAction.vue';
import ProductList from '@/components/product/ProductList.vue';
import ProductEdit from '@/components/product/ProductEdit.vue';
import { useProductStore } from '@/stores/productStore';
import {ref} from 'vue';
const productStore = useProductStore();

const isProductEditing = ref(false)
const editingProductId = ref(null)

function editCardClick(id) {
  editingProductId.value = id;
  isProductEditing.value = true;
}

async function confirmEditing(data) {
  const { id, product } = data;
  isProductEditing.value = false;
  await productStore.updateProduct(id, product);
}
</script>