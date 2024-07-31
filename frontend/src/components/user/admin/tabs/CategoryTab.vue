<template>
  <v-sheet class="mb-2 text-h6 pa-2 d-flex flex-row align-center justify-center" rounded>
    <v-btn-toggle v-model="mode" color="primary" mandatory rounded border>
      <v-btn value="add">Add</v-btn>
      <v-btn value="update">Update</v-btn>
      <v-btn value="delete">Delete</v-btn>
    </v-btn-toggle>
  </v-sheet>
  <v-sheet class="pa-2" rounded>
    <component :is="modeTab"/>
  </v-sheet>
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