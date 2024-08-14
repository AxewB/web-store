<template>
  <VSheet>
    <TabActionRow title="Добавить продукт">
      <ProductEdit
        @on-confirm="confirmAdding"/>
    </TabActionRow>
  </VSheet> 
</template>

<script setup>
import TabActionRow from '@/components/user/tabs/TabAction.vue';
import ProductEdit from '@/components/product/ProductEdit.vue';
import { useProductStore } from '@/stores/productStore';

const productStore = useProductStore();
async function confirmAdding(data) {
  const { product } = data
  await productStore.addProduct(product);
}
</script>