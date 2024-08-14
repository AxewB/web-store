<template>
  <VSheet>
    <TabActionRow  title="Удалить продукт">
      <VSheet
        class="bg-red"
        w-100
      >
        <ProductList
          type="card"
          @on-card-click="removeProduct"
        />
      </VSheet>
    </TabActionRow>
  </VSheet>
  <ConfirmDialog
    v-model="isConfirming"
    @on-confirm="confirmRemove"
  />
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
}

</script>