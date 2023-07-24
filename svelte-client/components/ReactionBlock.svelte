<script>
  import { onMount } from 'svelte';
  // import { fetchWithJwt } from '../lib/userAuthentication'
  // import { retrieveAuthUserId } from '../lib/userAuthentication'
  export let waveId

const CLAP = 'CLAP', LOVE = 'LOVE', LIKE = 'LIKE'
const reactionTypes = [{typeId: CLAP, icon: '👏'}, {typeId: LOVE, icon: '❤️'}, {typeId: LIKE, icon: '👍'}]

let clapsCount = 0
let heartsCount = 0
let likesCount = 0

let reactions = new Map()

const waveReactionsData = fetch(
  `/api/waves/${waveId}/wave-reactions`
  )
  .then((res) => res.json())
  .then(json => json._embedded.waveReactions)

const countReactions = (reactions, type) => reactions.filter(entry => entry.reaction == type)?.length || 0

onMount(async () => {
  const reactionsData = await waveReactionsData
  reactionTypes.forEach(type => {
    reactions.set(type.typeId, countReactions(reactionsData, type.typeId))
    reactions = reactions
  })
})


const incrementReaction = react => {
  console.log('Incrementing reaction '+ react)
  const reqBody = {
    date: new Date().toISOString(),
    reaction: react
  }
  fetch(`/api/waves/${waveId}/wave-reactions`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(reqBody)
  })
  .then(res => res.json())
  .then((resJson) => {
    if(resJson.id && resJson.reaction) {
      const currentCount = reactions.get(react)
      reactions.set(react, currentCount + 1)
      reactions = reactions
    }
  })
}
</script>

<style>
  .disabled {
    opacity: 0.5;
    cursor: default;
  }
</style>

{#await waveReactionsData}
  <p> Reactions coming up...</p>
{:then reactionsData}
<div class="reaction-container py-4 ml-6 flex justify-around items-center rounded-xl w-40 border border-waveyGreen">
  {#each reactionTypes as reactionType}
    <!-- class:disabled= { userId == retrieveAuthUserId() } -->
      <div
        class="transform transition-transform cursor-pointer hover:scale-110"
        on:click= { () => incrementReaction(reactionType.typeId) }
      >
        <div class="text-2xl">{reactionType.icon}</div>
        <div class="text-center">{ reactions.get(reactionType.typeId) }</div>
      </div>
    {/each}
  </div>
{:catch error}
    <p>{error}</p>
{/await}