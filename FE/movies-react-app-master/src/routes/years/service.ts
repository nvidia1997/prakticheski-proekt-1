import {BASE_URL} from "../../utils/api/constants";
import {getClient} from "../../utils/api/utils";
import {MovieYear} from "../movies/movies.typedef";

export async function list(): Promise<MovieYear[]> {
    // @ts-ignore
    return (await getClient().get(`${BASE_URL}/years`)).data;
}

export async function upsert(year: MovieYear): Promise<any> {
    // @ts-ignore
    return (await getClient().put(`${BASE_URL}/years`, year));
}

export async function deleteById(id: number): Promise<any> {
    // @ts-ignore
    return (await getClient().delete(`${BASE_URL}/years/${id}`));
}

export async function findById(id: number): Promise<MovieYear> {
    // @ts-ignore
    return (await getClient().get(`${BASE_URL}/years/${id}`)).data;
}
