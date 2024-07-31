<template>
  <v-sheet>
    <v-sheet>
      <v-text-field
        v-model="category.name"
        label="Name"
        hide-details
        class="mb-2"
      />
      <v-autocomplete 
        :items="categories"
        v-model="category.parent"
        item-title="name"
        item-value="id"
        label="parent"
        hide-details
        :disabled="isRoot"
      />
      <v-checkbox 
        label="Is root" 
        v-model="isRoot"
        hide-details
      />

    </v-sheet>
    <v-sheet class="d-flex justify-end" width="100%">
      <v-btn @click="emit('on-cancel')">Cancel</v-btn>
      <v-btn @click="confirmEditing()" class="ml-2" color="primary">Confirm</v-btn>
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import { computed, defineProps, onMounted, ref, watch } from 'vue'
import { useCategoryStore } from '@/stores/categoryStore';
const categoryStore = useCategoryStore();
const props = defineProps(['id'])
const emit = defineEmits(['on-close', 'on-confirm'])
const category = ref({
  "name": '',
  "parent": null,
})

const isRoot = ref(false)
const categories = computed(() => {
  return categoryStore.categories
})

const confirmEditing = () => {
  emit('on-confirm', {
    name: category.name,
    parent: isRoot ? null : category.parent,
  })
}

onMounted(async () => {
  if (props.id) {
    category.value = await categoryStore.getCategory(props.id)
    isRoot.value = category.value.parent === null
  }
})
</script>