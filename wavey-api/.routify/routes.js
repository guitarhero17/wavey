
/**
 * @roxi/routify 2.7.3
 * File generated Thu Jul 01 2021 00:10:14 GMT+0200 (Central European Summer Time)
 */

export const __version = "2.7.3"
export const __timestamp = "2021-06-30T22:10:14.061Z"

//buildRoutes
import { buildClientTree } from "@roxi/routify/runtime/buildRoutes"

//imports


//options
export const options = {}

//tree
export const _tree = {
  "root": true,
  "children": [
    {
      "isFallback": true,
      "path": "/_fallback",
      "component": () => import('../src/main/svelte/pages/_fallback.svelte').then(m => m.default)
    },
    {
      "isPage": true,
      "path": "/about",
      "id": "_about",
      "component": () => import('../src/main/svelte/pages/about.svelte').then(m => m.default)
    },
    {
      "isIndex": true,
      "isPage": true,
      "path": "/index",
      "id": "_index",
      "component": () => import('../src/main/svelte/pages/index.svelte').then(m => m.default)
    },
    {
      "isDir": true,
      "children": [
        {
          "isPage": true,
          "path": "/profile/:id",
          "id": "_profile__id",
          "component": () => import('../src/main/svelte/pages/profile/[id].svelte').then(m => m.default)
        },
        {
          "isPage": true,
          "path": "/profile/edit",
          "id": "_profile_edit",
          "component": () => import('../src/main/svelte/pages/profile/edit.svelte').then(m => m.default)
        },
        {
          "isIndex": true,
          "isPage": true,
          "path": "/profile/index",
          "id": "_profile_index",
          "component": () => import('../src/main/svelte/pages/profile/index.svelte').then(m => m.default)
        }
      ],
      "isLayout": true,
      "path": "/profile",
      "id": "_profile__layout",
      "component": () => import('../src/main/svelte/pages/profile/_layout.svelte').then(m => m.default)
    }
  ],
  "isLayout": true,
  "path": "/",
  "id": "__layout",
  "component": () => import('../src/main/svelte/pages/_layout.svelte').then(m => m.default)
}


export const {tree, routes} = buildClientTree(_tree)

