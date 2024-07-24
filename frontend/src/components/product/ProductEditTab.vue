<template>
  <v-sheet>
    <v-container>
      <v-row>
        <v-col cols="2">
          <v-sheet width="100px" height="100px">
            <v-img :src="product.thumbnail" width="200px"></v-img>
          </v-sheet>
        </v-col>
        <v-col class="d-flex flex-row align-center">
            <v-sheet 
              v-for="image in product.images"
              width="100px"
              height="100px"
              class="mx-1"
            >
              <v-hover v-slot="{ isHovering, props }">
                <v-card
                class="mx-auto"
                max-width="344"
                v-bind="props"
                elevation="0"
                border
              >
                <v-img :src="image" width="100px" border />
                <v-overlay
                  :model-value="isHovering"
                  class="align-center justify-center cursor-pointer"
                  scrim="rgba(0,0,0,0.7)"
                  contained
                >
                  <v-sheet class="text-button bg-transparent" >
                    REMOVE
                  </v-sheet>
                </v-overlay>
              </v-card>
              </v-hover>
            </v-sheet>
            <v-btn @click="addImage()" icon="mdi-plus" variant="plain" />
        </v-col>
      </v-row>
      <v-row>

      </v-row>
    </v-container>

    <v-sheet>
      <v-text-field v-for="key in Object.keys(product)" :label="key" :value="product[key]"></v-text-field>
    </v-sheet>
    <v-sheet class="d-flex justify-end" width="100%">
      <v-btn @click="emit('close')">Cancel</v-btn>
      <v-btn @click="editProduct()" class="ml-2" color="primary">Confirm</v-btn>
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import { useProductStore } from '@/stores/productStore';
import { defineProps, onMounted, ref } from 'vue'
import {useRouter} from 'vue-router'

const props = defineProps(['id'])
const emit = defineEmits(['close'])
const productStore = useProductStore();
const product = ref({})
function editProduct() {
  productStore.editProduct(props.id)
  emit('close')
}
function addImage() {
  alert("add image") // TODO: add image functionality
}

onMounted(async () => {
  product.value = await productStore.getProduct(props.id)
})
</script>