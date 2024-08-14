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
  <VSheet class="pa-2" rounded>
    <Component :is="modeTab"/>
  </VSheet>
</template>

<script setup>
import { shallowRef, watch, onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import { useCategoryStore } from '@/stores/categoryStore';
import { CategoryTabAdd, CategoryTabEdit, CategoryTabDelete } from './adminTabs.js';

const userStore = useUserStore();
const categoryStore = useCategoryStore();
const router = useRouter();
const mode = ref('add')
const modeTab = shallowRef(CategoryTabAdd)

watch(mode, (value) => {
  modeTab.value = modeToTab(value)
})
const modeToTab = (mode) => {
  switch (mode) {
    case 'add': return CategoryTabAdd
    case 'update': return CategoryTabEdit
    case 'delete': return CategoryTabDelete
    default:
      mode.value = 'add'
      return CategoryTabAdd
  }
}

onMounted(() => {
  if (!userStore.user.roles.includes("ROLE_ADMIN")) {
    router.push('/');
  }
  categoryStore.getCategories();
})
</script>