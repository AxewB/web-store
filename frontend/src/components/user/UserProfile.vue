<template>
  <v-sheet v-if="currentUser" height="100%" class="d-flex flex-column bg-transparent">
      <AppHeader/>

      <v-container class="flex-grow-1" >
        <v-row  class="fill-height">
          <v-col class="ma-1" cols="2">
            <v-row justify="center" class="pa-2">
              <v-sheet class="d-flex flex-column align-center pa-2" elevation="10" width="100%" rounded>
              <v-avatar size="200px" rounded :image="currentUser.avatar ? currentUser.avatar : 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmCy16nhIbV3pI1qLYHMJKwbH2458oiC9EmA&s'"/>
            </v-sheet>
            </v-row>
            <v-row justify="center" class="pa-2">
              <v-list nav rounded density='compact' width="100%" v-model="window" elevation="10">
                <v-list-item
                  link
                  @click="window = UserInfo"
                  :active="window === UserInfo"
                  prepend-icon="mdi-account"
                  title="Account"/>
                  <v-list-item
                  link
                  @click="window = UserOrders"
                  :active="window === UserOrders"
                  prepend-icon="mdi-package-variant"
                  title="Orders"/>
                  <v-list-item
                  link
                  @click="window = UserPayment"
                  :active="window === UserPayment"
                  prepend-icon="mdi-credit-card"
                  title="Payment"/>
                  <v-list-item
                  link
                  @click="window = UserSettings"
                  :active="window === UserSettings"
                  prepend-icon="mdi-cog"
                  title="Settings"/>
                  <div v-if="userStore.user.roles.includes('ROLE_ADMIN')">
                    <v-list-item>
                      <VDivider/>
                    </v-list-item>
                    <v-list-item
                      link
                      @click="window = ProductAdminPage"
                      :active="window === ProductAdminPage"
                      prepend-icon="mdi-book-edit"
                      title="Edit products"/>
                  </div>
                <v-list-item>
                  <VDivider/>
                </v-list-item>
                <v-list-item
                  @click="logout"
                  color="error"
                  width="100%"
                  title="Logout"
                  prepend-icon="mdi-logout"/>
              </v-list>
            </v-row>
          </v-col>
          <v-col>
            <v-sheet elevation="3" rounded>
              <component :is="window"/>
            </v-sheet>
          </v-col>
        </v-row>
      </v-container>
  </v-sheet>
</template>

<script setup>
import AppHeader from '@/components/AppHeader.vue'
import { computed, onMounted, ref, shallowRef } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';
import { UserInfo, UserPayment, UserOrders, UserSettings } from '@/components/user/UserProfileTabs.js';
import ProductAdminPage from '../product/ProductAdminTab.vue';
const router = useRouter();
const userStore = useUserStore();
const window = shallowRef(UserInfo)
const currentUser = computed(() => {return userStore.user});

const logout = () => {
  userStore.logout();
}

const loggedIn = computed(() => {
    return userStore.status.loggedIn;
  },
)
onMounted(() => {
  if (!loggedIn.value) {
    router.push('/user/login');
  }
})
</script>