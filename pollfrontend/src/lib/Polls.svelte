<script>
    import user from "../storage/store.js";
    import Vote from "./Vote.svelte";

    let username = $user.username;
    let currentError = null;

    const result = fetch("http://localhost:8080/polls").then((response) => response.json())

    const convertToDate = (timestamp) => {
        const date = new Date(timestamp);  // Convert string to Date object
        return date.toLocaleString();       // Format the Date as a localized string
    }
</script>

<div class="polls">
    {#await result}
        its loading…
    {:then ready}
        {#each ready as poll}
            <div class="poll">
                <h3>{poll.question}</h3>

                <p>Poll created: {convertToDate(poll.publishedAt)}</p>
                <p>Poll is valid until: {convertToDate(poll.validUntil)}</p>
                <Vote pollID={poll.pollID}/>
            </div>
        {/each}
    {:catch error}
        {error}
    {/await}
</div>

<style>
    .polls {
        display: grid;
        grid-column: min(2);
    }
    .poll {
        background-color: #e8eeeb;
        border-radius: 0.2rem;
        padding: 1rem 5rem 1rem 5rem;
        width: auto;
    }
</style>