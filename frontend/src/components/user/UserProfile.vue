<template>
  <v-sheet v-if="currentUser" height="100%" class="d-flex flex-column bg-transparent">
      <AppHeader/>
      <v-sheet class="d-flex mt-4 flex-row bg-transparent">
        <v-sheet class="mr-2 bg-transparent" min-width="200px">
          <v-list rounded width="100%" v-model="window" elevation="10" class="mb-2">
            <v-list-item
              prepend-icon="mdi-crown"
              :title="currentUser.username"/>
          </v-list>

          <v-list nav rounded density='compact' width="100%" v-model="window" elevation="10">
            <v-list-item
              link
              @click="switchTab('account')"
              :active="window === UserInfo"
              prepend-icon="mdi-account"
              title="Account"/>
              <v-list-item
              link
              @click="switchTab('orders')"
              :active="window === UserOrders"
              prepend-icon="mdi-package-variant"
              title="Orders"/>
              <v-list-item
              link
              @click="switchTab('settings')"
              :active="window === UserSettings"
              prepend-icon="mdi-cog"
              title="Settings"/>
            
          </v-list>
          <v-list
            v-if="userStore.user.roles.includes('ROLE_ADMIN')"
            v-model="window"
            class="mt-2"
            nav
            rounded
            density='compact'
            width="100%"
            elevation="10"
          >
            <v-list-item
              link
              :to="{ name: 'admin' }"
              prepend-icon="mdi-security"
              title="Admin panel"
            />
          </v-list>
          <v-list
            class="mt-2 bg-transparent"
            nav
            rounded
            density='compact'
            width="100%"
          >
            <v-list-item
              @click="logout"
              color="error"
              width="100%"
              title="Logout"
              prepend-icon="mdi-logout"
              border
            />
          </v-list>
        </v-sheet>
        <v-sheet class="flex-grow-1 bg-transparent">
          <v-sheet elevation="3" rounded>
            <component :is="window"/>
          </v-sheet>
        </v-sheet>
      </v-sheet>
  </v-sheet>
</template>

<script setup>
import AppHeader from '@/components/AppHeader.vue'
import { computed, onMounted, ref, shallowRef, watch } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter, useRoute } from 'vue-router';
import { UserInfo, UserOrders, UserSettings } from '@/components/user/UserProfileTabs.js';
const router = useRouter();
const route = useRoute();
const userStore = useUserStore();
const window = shallowRef(UserInfo)
const currentUser = computed(() => {return userStore.user});

const logout = () => {
  userStore.logout();
}

const loggedIn = computed(() => userStore.status.loggedIn)
const tab = computed(() => route.query.tab)

watch(tab, (value) => {
  window.value = getWindow(value)
})
function getWindow(tab) {
  switch (tab) {
    case 'account': return UserInfo
    case 'orders': return UserOrders
    case 'settings': return UserSettings
    default: return UserInfo
  }
}
function switchTab(tab) {
  router.push({query: {tab}})
}
onMounted(() => {
  if (!loggedIn.value) {
    router.push('/user/login');
  }
})
</script>