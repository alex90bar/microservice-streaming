create
user "streaming";
alter
user "streaming" with PASSWORD 'streaming';
create schema "streaming";
alter
schema "streaming" owner to "streaming";
