<template>
  <v-list-group 
    v-if="children.length > 0"
    :value="category.name" 
  >
    <template v-slot:activator="{ props }">
      <v-list-item
        v-bind="props"
        :title="category.name"
        :value="category"
        @click="categoryClick(category)"
      ></v-list-item>
    </template>
    <catalogue-category-item 
      v-for="child in children"
      :key="child.id + 'child'"
      :category="child"
      :value="child"
      @click="categoryClick(child)"
    />
  </v-list-group>
  <v-list-item 
    v-else
    link 
    :value="category"
    @click="categoryClick(category)"
  >
    {{ category.name }}
  </v-list-item>
</template>


<script setup>
import { onMounted, ref } from 'vue';
import { useCategoryStore } from '@/stores/categoryStore';
const categoryStore = useCategoryStore();
const props = defineProps({
  category: {
    type: Object,
    required: true
  }
})

const children = ref({})

const emit = defineEmits(['on-category-click'])

const categoryClick = (category) => {
  emit('on-category-click', category)
}

onMounted(async () => {
  children.value = await categoryStore.getChildren(props.category.id)
})
</script>