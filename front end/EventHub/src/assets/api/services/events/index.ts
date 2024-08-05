import { axiosAPI } from "../../index"

export interface EventDTO {
    id:Number,
    address:String,
    description:String,
    finalDate:String,
    initialDate:String,
    maximumCapacity:Number,
    name:String,
    type:String
}

let config:object = {
    headers: {
        'Accept': 'application/json;',
        'Content-Type': 'application/json',
    }
}

export async function apiGetEventList() {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get('/event/list', config);
}

export async function apiGetEventById(eventId:Number) {
    const token = localStorage.getItem("token");
    config = {
        headers: {
            'Accept': 'application/json;',
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        }
    }
    return axiosAPI.get(`/event/${eventId}`, config);
}