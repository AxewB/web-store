<template>
  <v-container height="100%" class="d-flex flex-column">
    <TabActionRow title="Add product">

      <v-btn @click="userStore.addProduct()">Add product</v-btn>
    </TabActionRow>
    <TabActionRow title="Edit product">
      <v-sheet v-if="isProductEditing">
        <ProductEditTab :id="editingProduct" @on-close="isProductEditing = false" @on-confirm="confirmEditing"></ProductEditTab>
      </v-sheet>
      <v-sheet v-else width="100%" class=bg-red>
        <ProductList type="row" @on-card-click="cardClicked"></ProductList>
      </v-sheet>
    </TabActionRow>
  </v-container>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import AppHeader from '../AppHeader.vue';
import { useUserStore } from '@/stores/userStore';
import TabActionRow from '../user/tabs/TabAction.vue';
import { useRouter } from 'vue-router';
import ProductList from './ProductList.vue';
import { useProductStore } from '@/stores/productStore';

const userStore = useUserStore();
const productStore = useProductStore();
const router = useRouter();
const isProductEditing = ref(false)
const editingProduct = ref({})

function cardClicked(product) {
  editingProduct.value = product;
  isProductEditing.value = true;
}

function confirmEditing() {
  editingProduct.value = false;
  productStore.editProduct(props.product, )
}

onMounted(() => {
  if (!userStore.user.roles.includes("ROLE_ADMIN")) {
    router.push('/');
  }
  productStore.limit = 5;
  productStore.getProducts();
})
</script>