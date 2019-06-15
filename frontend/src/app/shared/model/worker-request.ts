import { User } from './user';
import { PlayingField } from './google-map';

export interface WorkerRequest {
    user: User;
    status: string;
    fileName: string;
    proofOfWork: Blob;
    playingField: PlayingField;
}
