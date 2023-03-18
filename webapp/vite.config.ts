import { resolve } from 'path';
import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  root: resolve(__dirname, 'src'),
  envDir: resolve(__dirname, '.'), // .envファイルの配置ディレクトリ
  publicDir: resolve(__dirname, 'public'),
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  build: {
    outDir: resolve(__dirname, '../src/main/resources/static'),
    rollupOptions: {
      input: {
        index: resolve(__dirname, 'src/index.html'),
        login: resolve(__dirname, 'src/login.html'),
        admin: resolve(__dirname, 'src/admin.html'),
      },
    },
  }
})
