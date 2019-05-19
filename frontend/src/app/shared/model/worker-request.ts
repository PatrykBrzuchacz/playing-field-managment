import { User } from './user';

export interface WorkerRequest {
    user: User;
    status: string;
    fileName: string;
    proofOfWork: Blob;
}