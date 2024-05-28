cd core/note

mvn clean install

docker image build -t note .

cd ../KnoteUI

docker image build -t ui .

cd ../..

docker-compose up -d