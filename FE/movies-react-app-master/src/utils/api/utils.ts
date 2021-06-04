import Axios from "axios";

export const getClient = () => Axios.create({
    headers: {
        'Access-Control-Allow-Origin': '*',
    },
});
