const express = require('express');
const cors = require('cors');
const app = express();

app.use(cors());
app.use(express.json());

// GET Method
app.get('/bfhl', (req, res) => {
    res.status(200).json({ operation_code: 1 });
});

// POST Method
app.post('/bfhl', (req, res) => {
    try {
        // 1. Validation
        if (!req.body) {
            return res.status(400).json({
                is_success: false,
                message: "Request body is missing"
            });
        }

        const data = req.body.data;
        if (!data) {
            return res.status(400).json({
                is_success: false,
                message: "data field is missing"
            });
        }

        if (!Array.isArray(data)) {
            return res.status(400).json({
                is_success: false,
                message: "data field must be an array"
            });
        }

        const even_numbers = [];
        const odd_numbers = [];
        const alphabets = [];
        const special_characters = [];
        let sum = 0;
        let concat_source = "";

        // 2. Categorization logic
        for (const item of data) {
            if (item === null || item === undefined) {
                continue;
            }
            const strItem = String(item);
            const trimmed = strItem.trim();

            if (trimmed === "") {
                special_characters.push(strItem);
            } else if (/^[0-9]+$/.test(trimmed)) {
                // Number
                const val = parseInt(trimmed, 10);
                if (!isNaN(val)) {
                    sum += val;
                    if (val % 2 === 0) {
                        even_numbers.push(trimmed);
                    } else {
                        odd_numbers.push(trimmed);
                    }
                } else {
                    special_characters.push(strItem);
                }
            } else if (/^[a-zA-Z]+$/.test(trimmed)) {
                // Alphabet
                alphabets.push(trimmed.toUpperCase());
                concat_source += trimmed;
            } else {
                // Special character
                special_characters.push(strItem);
            }
        }

        // 3. Alternating Caps Reverse Concatenated string
        let concat_string = "";
        if (concat_source.length > 0) {
            const reversed = concat_source.split("").reverse().join("");
            const alternating = [];
            for (let i = 0; i < reversed.length; i++) {
                const char = reversed[i];
                if (i % 2 === 0) {
                    alternating.push(char.toUpperCase());
                } else {
                    alternating.push(char.toLowerCase());
                }
            }
            concat_string = alternating.join("");
        }

        // 4. Response
        res.status(200).json({
            is_success: true,
            user_id: "aditi_dhoni_24032005",
            email: "aditidhoni231188@acropolis.in",
            roll_number: "0827IT231008",
            even_numbers,
            odd_numbers,
            alphabets,
            special_characters,
            sum: String(sum),
            concat_string
        });

    } catch (error) {
        res.status(500).json({
            is_success: false,
            message: "An internal server error occurred: " + error.message
        });
    }
});

// Root route
app.get('/', (req, res) => {
    res.send('BFHL REST API deployed on Vercel is online.');
});

// For local testing/running if needed
const PORT = process.env.PORT || 8080;
if (require.main === module) {
    app.listen(PORT, () => {
        console.log(`Server running on port ${PORT}`);
    });
}

module.exports = app;
