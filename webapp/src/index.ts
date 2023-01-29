import { createApp } from 'vue'
import App from './Index.vue'
import router from './router/index'

import "bootstrap/dist/css/bootstrap.min.css"
import './assets/main.css'

createApp(App).use(router).mount("#app");
