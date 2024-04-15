cd core/note

mvn clean install

docker image build -t note .

cd ../..

docker-compose up