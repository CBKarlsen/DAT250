<script>
    import user from "../storage/store.js";
    import poll from "../storage/poll.js";

    let username = $user.username;
    let question = '';
    let validUntil = null;
    let isPublic = false;

    let vo1 = "";
    let vo2 = "";
    let voteOptions = [];
    let numVOs = 0;

    let currentError = null;

    const formatDate = (validUntil) => {
        if (validUntil) {
            return new Date(validUntil).toISOString();
        }
        return null
    }

    const addVoteOption = (caption) => {
        voteOptions.push({
            "caption": caption,
            "presentationOrder": numVOs
        })
        numVOs += 1;
    }

    const createPoll = () => {
        const formattedDate = formatDate(validUntil);
        addVoteOption(vo1);
        addVoteOption(vo2);

        fetch("http://localhost:8080/polls", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username:username, question: question, validUntil: formattedDate, isPublic: isPublic, voteOptions: voteOptions
            })
        })
            .then((res) => {
                if (res.status < 299) return res.json()
                if (res.status > 299) currentError = "Something wrong with server response";
            })
            .then((data) => {
                if (data) poll.set(data)
            })
            .catch((err) => {
                currentError = err;
                console.log("Error creating poll: ", err)
            })
    }
</script>

<form on:submit|preventDefault={createPoll}>
    <div>
        <label for="quesion">Question</label>
        <input type="text" id="quesion" bind:value={question}/>
    </div>
    <div>
        <label for="vo1">Option 1</label>
        <input type="text" id="vo1" bind:value={vo1}/>
    </div>
    <div>
        <label for="vo2">Option 2</label>
        <input type="text" id="vo2" bind:value={vo2}/>
    </div>
    <div>
        <label for="validUntil">Deadline for poll</label>
        <input type="datetime-local" id="validUntil" bind:value={validUntil}/>
    </div>
    <button type="submit">Create poll</button>
</form>