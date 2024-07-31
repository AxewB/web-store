<template>
  <v-sheet>
    <TabActionRow  title="Edit category">
      <v-sheet v-if="editingMode === false">
        <CategoryList @on-click="editCategory"/>
      </v-sheet>

      <v-sheet v-else>
        <CategoryEdit 
          @on-confirm="confirmEditing"
          @on-cancel="editingMode = false"
          :id="chosenCategoryId"/>
      </v-sheet>
    </TabActionRow>
  </v-sheet>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useCategoryStore } from '@/stores/categoryStore';
import TabActionRow from '@/components/user/tabs/TabAction.vue';
import CategoryList from '@/components/category/CategoryList.vue';

const categoryStore = useCategoryStore();

const editingMode = ref(false);
const chosenCategoryId = ref({});

const editCategory = (category) => {
  editingMode.value = true
  chosenCategoryId.value = category.id
}

const confirmEditing = async (data) => {
  await categoryStore.updateCategory(chosenCategoryId.value, data)
}

onMounted(async () => {
  await categoryStore.getCategories();
})
</script>