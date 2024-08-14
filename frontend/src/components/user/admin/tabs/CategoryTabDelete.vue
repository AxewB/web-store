<template>
  <VSheet>
    <TabActionRow  title="Удалить категорию">
      <CategoryList @on-click="categoryClick"/>
    </TabActionRow>
  </VSheet>
  <ConfirmDialog
    v-model="isConfirming"
    @on-confirm="confirmDelete"
    @on-cancel="chosenCategoryId = null"
  />
</template>

<script setup>
import {ref} from "vue";
import TabActionRow from '@/components/user/tabs/TabAction.vue';
import ConfirmDialog from '@/components/ConfirmDialog.vue';
import { useCategoryStore } from '@/stores/categoryStore';
import CategoryList from '@/components/category/CategoryList.vue';
const categoryStore = useCategoryStore();

const chosenCategoryId = ref(null);
const isConfirming = ref(false);

const categoryClick = (category) => {
  chosenCategoryId.value = category.id
  isConfirming.value = true
}

const confirmDelete = async () => {
  await categoryStore.deleteCategory(chosenCategoryId.value)
}
</script>
