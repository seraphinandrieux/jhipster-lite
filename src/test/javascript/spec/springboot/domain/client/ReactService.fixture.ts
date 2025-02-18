import sinon, { SinonStub } from 'sinon';
import { ReactService } from '@/springboot/domain/client/ReactService';

export interface ReactServiceFixture extends ReactService {
  add: SinonStub;
  addWithStyle: SinonStub;
}

export const stubReactService = (): ReactServiceFixture => ({
  add: sinon.stub(),
  addWithStyle: sinon.stub(),
});
