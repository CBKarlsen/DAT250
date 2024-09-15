<script>
    let username = '';
    let otherUserDetails = {}; // Add fields as needed

    async function createUser() {
        const newUser = { username };
        const response = await fetch(`/users/${username}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
        });

        if (response.ok) {
            console.log('User created successfully!');
            // Optionally, redirect to the user details page or user list
        } else {
            console.error('Error creating user:', response.statusText);
            // Handle error
        }
    }
</script>

<h2>Create User</h2>

<form on:submit|preventDefault={createUser}>
    <input type="text" bind:value={username} placeholder="Username">
    <!-- Add other fields as needed -->
    <button type="submit">Create</button>
</form>