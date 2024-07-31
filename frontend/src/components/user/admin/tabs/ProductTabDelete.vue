<template>
  <v-sheet>
    <TabActionRow  title="Delete product">
      <v-sheet w-100 class="bg-red">
        <ProductList
          type="card"
          @on-card-click="removeProduct"
        />
      </v-sheet>
    </TabActionRow>
  </v-sheet>
  <ConfirmDialog v-model="isConfirming" @on-confirm="confirmRemove" />
</template>

<script setup>
import {ref} from 'vue';
import { useProductStore } from '@/stores/productStore';
import { useRouter } from 'vue-router';
import ConfirmDialog from '@/components/ConfirmDialog.vue';
import ProductList from '@/components/product/ProductList.vue';
import TabActionRow from '@/components/user/tabs/TabAction.vue';

const productStore = useProductStore();
const router = useRouter();
const isConfirming = ref(false);
const id = ref({})

function removeProduct(newId) {
  isConfirming.value = true
  id.value = newId
}

async function confirmRemove() {
  await productStore.deleteProduct(id.value)
  // router.go();
}

</script>