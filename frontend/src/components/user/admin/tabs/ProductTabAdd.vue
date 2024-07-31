<template>
  <v-sheet>
    <TabActionRow title="Add product">
      <ProductEdit
        @on-confirm="confirmAdding"/>
    </TabActionRow>
  </v-sheet>  
</template>

<script setup>
import TabActionRow from '@/components/user/tabs/TabAction.vue';
import ProductEdit from '@/components/product/ProductEdit.vue';
import { useProductStore } from '@/stores/productStore';

const productStore = useProductStore();
async function confirmAdding(data) {
  const { product } = data
  console.log(product)
  await productStore.addProduct(product);
}
</script>