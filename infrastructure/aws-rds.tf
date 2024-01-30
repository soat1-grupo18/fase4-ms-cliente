data "aws_vpc" "fiap" {
  tags = {
    "Name" = "fiap-vpc"
  }
}

data "aws_subnets" "private" {
  tags = {
    "fiap-private-subnet" = "true"
  }
}

resource "aws_db_subnet_group" "fiap_ms_cliente" {
  name       = "fiap-ms-cliente"
  subnet_ids = data.aws_subnets.private.ids
}

resource "aws_db_instance" "fiap_ms_cliente" {
  allocated_storage       = 20
  storage_type            = "gp2"
  engine                  = "postgres"
  engine_version          = "15.3"
  instance_class          = "db.t3.micro"
  identifier              = "fiap-ms-cliente"
  db_name                 = "fiap_ms_cliente"
  username                = "dbuser"     # Insecure. It should use IAM authentication OR this should be hidden.
  password                = "dbpassword" # Insecure. It should use IAM authentication OR this should be hidden.
  parameter_group_name    = "default.postgres15"
  skip_final_snapshot     = true
  backup_retention_period = 0
  apply_immediately       = true

  db_subnet_group_name = aws_db_subnet_group.fiap_ms_cliente.name
  vpc_security_group_ids = [
    aws_security_group.rds_fiap_ms_cliente.id
  ]
}

resource "aws_security_group" "rds_fiap_ms_cliente" {
  name   = "rds-fiap-ms-cliente"
  vpc_id = data.aws_vpc.fiap.id

  ingress {
    description = "Same VPC"
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = data.aws_vpc.fiap.cidr_block_associations[*].cidr_block
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
