<template>
  <VSheet class="mb-2 text-h6 pa-2 d-flex flex-row align-center justify-center" rounded>
    <VBtnToggle
      v-model="mode"
      color="primary"
      mandatory
      rounded
      border
      >
      <VBtn value="add">
        Добавить
      </VBtn>
      <VBtn value="update">
        Изменить
      </VBtn>
      <VBtn value="delete">
        Удалить
      </VBtn>
    </VBtnToggle>
  </VSheet>
  <VSheet
    class="pa-2"
    rounded
  >
    <component :is="modeTab"/>
  </VSheet>
</template>

<script setup>
import { computed, onMounted, ref, shallowRef, watch } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useProductStore } from '@/stores/productStore';
import { useRouter, useRoute } from 'vue-router';
import { ProductTabAdd, ProductTabEdit, ProductTabDelete } from './adminTabs.js';

const userStore = useUserStore();
const productStore = useProductStore();
const router = useRouter();
const route = useRoute();

const mode = ref('add')
const modeTab = shallowRef(ProductTabAdd)
watch(mode, (value) => {
  modeTab.value = modeToTab(value)
})
const modeToTab = (mode) => {
  switch (mode) {
    case 'add': return ProductTabAdd
    case 'update': return ProductTabEdit
    case 'delete': return ProductTabDelete
    default:
      mode.value = 'add'
      return ProductTabAdd
  }
}


onMounted(() => {
  if (!userStore.user.roles.includes("ROLE_ADMIN")) {
    router.push('/');
  }
  productStore.limit = 5;
  productStore.fetchProducts();
  if (route.query.mode) {
    mode.value = route.query.mode
  }
})
</script>