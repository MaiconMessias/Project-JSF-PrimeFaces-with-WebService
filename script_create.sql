--DBCRUDJsfPrimefacesHibernate

create table cargo(
                    id serial not null, 
                    descricao varchar(100), 
                    salario numeric(6,2),
                    constraint pk_cargo primary key(id)
                    );
                    
create table funcionario (
                           id serial not null, 
                           nome varchar(100), 
                           cpf varchar(20), 
                           rg varchar(20), 
                           id_cargo integer,
    					   constraint pk_funcionario primary key(id),
    					   constraint fk_funcionario_cargo foreign key(id_cargo) references cargo(id) 	
                           );                 
                           
create table contato_funcionario (
                                   id_funcionario integer not null, 
                                   cel varchar(20) not null, 
                                   email varchar(40),
    							   constraint pk_contato_funcionario primary key(id_funcionario, cel),
    							   constraint fk_contato_funci_funci foreign key(id_funcionario) 
                                                                       references funcionario(id)
                                   );
                                   
create table fornecedor (
                          cnpj varchar(20) not null, 
    					  nome_fantasia varchar(100),
    					  constraint pk_fornecedor primary key(cnpj)
                          );
                          
create table produto (
                       id serial not null,
    				   descricao varchar(100),
    				   detalhe varchar(300),
    				   preco_compra numeric(6,2),
    				   preco_venda numeric(6,2),
    				   id_fornecedor varchar(20),
    				   constraint pk_produto primary key(id),
    				   constraint fk_produto_fornecedor foreign key(id_fornecedor) 
                                                          references fornecedor(cnpj)
                       );
                       
create table cliente (
                       id serial not null,
    				   nome varchar(100),
    				   cpf varchar(20),
    				   constraint pk_cliente primary key(id)
                       );
                       
create table contato_cliente(
                              id_cliente integer not null, 
                              cel varchar(20) not null, 
                              email varchar(40),
    						  constraint pk_contato_cliente primary key(id_cliente, cel),
    						  constraint fk_contato_cliente_cliente foreign key(id_cliente)
                                                                       references cliente(id)
                              );
                              
create table venda (
                     id serial not null,
    				 dt date,
    				 id_funcionario integer,
    				 id_cliente integer,
    			     constraint pk_venda primary key(id),
    				 constraint fk_venda_funcionario foreign key(id_funcionario) 
                                                       references funcionario(id),
                     constraint fk_venda_cliente foreign key(id_cliente)
                                                   references cliente(id)         
                     );       
                     
create table item_venda (
                          id_venda integer not null, 
                          id_produto integer not null,
    					  qtde numeric(6),
    					  constraint pk_item_venda primary key(id_venda, id_produto),
    					  constraint fk_item_venda_venda foreign key(id_venda)  
                                                           references venda(id),
                          constraint fk_item_venda_produto foreign key(id_produto)
                                                            references produto(id)  
                          );
                          
create table usuario (id serial not null, 
                       usuario varchar(40), 
                       senha varchar(20),
                       id_funcionario integer,
                       constraint pk_usuario primary key(id),
                       constraint fk_usuario_funcionario foreign key(id_funcionario) references funcionario(id)
                       );                                                                                                                                                                                                               