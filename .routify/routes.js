
/**
 * @roxi/routify 2.18.12
 * File generated Fri Jun 30 2023 01:20:32 GMT+0200 (Central European Summer Time)
 */

export const __version = "2.18.12"
export const __timestamp = "2023-06-29T23:20:32.495Z"

//buildRoutes
import { buildClientTree } from "@roxi/routify/runtime/buildRoutes"

//imports


//options
export const options = {}

//tree
export const _tree = {
  "name": "_layout",
  "filepath": "/_layout.svelte",
  "root": true,
  "ownMeta": {},
  "absolutePath": "/Users/dobromir/Documents/projects/wavey/svelte-client/pages/_layout.svelte",
  "children": [
    {
      "isFile": true,
      "isDir": false,
      "file": "_fallback.svelte",
      "filepath": "/_fallback.svelte",
      "name": "_fallback",
      "ext": "svelte",
      "badExt": false,
      "absolutePath": "/Users/dobromir/Documents/projects/wavey/svelte-client/pages/_fallback.svelte",
      "importPath": "../svelte-client/pages/_fallback.svelte",
      "isLayout": false,
      "isReset": false,
      "isIndex": false,
      "isFallback": true,
      "isPage": false,
      "ownMeta": {},
      "meta": {
        "recursive": true,
        "preload": false,
        "prerender": true
      },
      "path": "/_fallback",
      "id": "__fallback",
      "component": () => import('../svelte-client/pages/_fallback.svelte').then(m => m.default)
    },
    {
      "isFile": true,
      "isDir": false,
      "file": "about.svelte",
      "filepath": "/about.svelte",
      "name": "about",
      "ext": "svelte",
      "badExt": false,
      "absolutePath": "/Users/dobromir/Documents/projects/wavey/svelte-client/pages/about.svelte",
      "importPath": "../svelte-client/pages/about.svelte",
      "isLayout": false,
      "isReset": false,
      "isIndex": false,
      "isFallback": false,
      "isPage": true,
      "ownMeta": {},
      "meta": {
        "recursive": true,
        "preload": false,
        "prerender": true
      },
      "path": "/about",
      "id": "_about",
      "component": () => import('../svelte-client/pages/about.svelte').then(m => m.default)
    },
    {
      "isFile": true,
      "isDir": false,
      "file": "index.svelte",
      "filepath": "/index.svelte",
      "name": "index",
      "ext": "svelte",
      "badExt": false,
      "absolutePath": "/Users/dobromir/Documents/projects/wavey/svelte-client/pages/index.svelte",
      "importPath": "../svelte-client/pages/index.svelte",
      "isLayout": false,
      "isReset": false,
      "isIndex": true,
      "isFallback": false,
      "isPage": true,
      "ownMeta": {},
      "meta": {
        "recursive": true,
        "preload": false,
        "prerender": true
      },
      "path": "/index",
      "id": "_index",
      "component": () => import('../svelte-client/pages/index.svelte').then(m => m.default)
    },
    {
      "isFile": true,
      "isDir": true,
      "file": "_layout.svelte",
      "filepath": "/profile/_layout.svelte",
      "name": "_layout",
      "ext": "svelte",
      "badExt": false,
      "absolutePath": "/Users/dobromir/Documents/projects/wavey/svelte-client/pages/profile/_layout.svelte",
      "children": [
        {
          "isFile": true,
          "isDir": false,
          "file": "[username].svelte",
          "filepath": "/profile/[username].svelte",
          "name": "[username]",
          "ext": "svelte",
          "badExt": false,
          "absolutePath": "/Users/dobromir/Documents/projects/wavey/svelte-client/pages/profile/[username].svelte",
          "importPath": "../svelte-client/pages/profile/[username].svelte",
          "isLayout": false,
          "isReset": false,
          "isIndex": false,
          "isFallback": false,
          "isPage": true,
          "ownMeta": {},
          "meta": {
            "recursive": true,
            "preload": false,
            "prerender": true
          },
          "path": "/profile/:username",
          "id": "_profile__username",
          "component": () => import('../svelte-client/pages/profile/[username].svelte').then(m => m.default)
        },
        {
          "isFile": true,
          "isDir": false,
          "file": "edit.svelte",
          "filepath": "/profile/edit.svelte",
          "name": "edit",
          "ext": "svelte",
          "badExt": false,
          "absolutePath": "/Users/dobromir/Documents/projects/wavey/svelte-client/pages/profile/edit.svelte",
          "importPath": "../svelte-client/pages/profile/edit.svelte",
          "isLayout": false,
          "isReset": false,
          "isIndex": false,
          "isFallback": false,
          "isPage": true,
          "ownMeta": {},
          "meta": {
            "recursive": true,
            "preload": false,
            "prerender": true
          },
          "path": "/profile/edit",
          "id": "_profile_edit",
          "component": () => import('../svelte-client/pages/profile/edit.svelte').then(m => m.default)
        },
        {
          "isFile": true,
          "isDir": false,
          "file": "index.svelte",
          "filepath": "/profile/index.svelte",
          "name": "index",
          "ext": "svelte",
          "badExt": false,
          "absolutePath": "/Users/dobromir/Documents/projects/wavey/svelte-client/pages/profile/index.svelte",
          "importPath": "../svelte-client/pages/profile/index.svelte",
          "isLayout": false,
          "isReset": false,
          "isIndex": true,
          "isFallback": false,
          "isPage": true,
          "ownMeta": {},
          "meta": {
            "recursive": true,
            "preload": false,
            "prerender": true
          },
          "path": "/profile/index",
          "id": "_profile_index",
          "component": () => import('../svelte-client/pages/profile/index.svelte').then(m => m.default)
        }
      ],
      "isLayout": true,
      "isReset": false,
      "isIndex": false,
      "isFallback": false,
      "isPage": false,
      "importPath": "../svelte-client/pages/profile/_layout.svelte",
      "ownMeta": {},
      "meta": {
        "recursive": true,
        "preload": false,
        "prerender": true
      },
      "path": "/profile",
      "id": "_profile__layout",
      "component": () => import('../svelte-client/pages/profile/_layout.svelte').then(m => m.default)
    }
  ],
  "isLayout": true,
  "isReset": false,
  "isIndex": false,
  "isFallback": false,
  "isPage": false,
  "isFile": true,
  "file": "_layout.svelte",
  "ext": "svelte",
  "badExt": false,
  "importPath": "../svelte-client/pages/_layout.svelte",
  "meta": {
    "recursive": true,
    "preload": false,
    "prerender": true
  },
  "path": "/",
  "id": "__layout",
  "component": () => import('../svelte-client/pages/_layout.svelte').then(m => m.default)
}


export const {tree, routes} = buildClientTree(_tree)
